package com.epam.xstack.service.authentication_service.impl;

import com.epam.xstack.aspects.authentication_aspects.annotations.AuthenticationChangeLoginAspectAnnotation;
import com.epam.xstack.configuration.JwtService;
import com.epam.xstack.exceptions.exception.UserNameOrPasswordNotCorrectException;
import com.epam.xstack.exceptions.generator.PasswordUserNameGenerator;
import com.epam.xstack.exceptions.validator.UserNameCorrectValidation;
import com.epam.xstack.mapper.authentication_mapper.AuthenticationChangeLoginRequestMapper;
import com.epam.xstack.models.dto.authentication_dto.AuthenticationChangeLoginRequestDTO;
import com.epam.xstack.models.dto.authentication_dto.AuthenticationChangeLoginResponseDTO;
import com.epam.xstack.models.dto.authentication_dto.AuthenticationRequestDTO;
import com.epam.xstack.models.dto.authentication_dto.AuthenticationResponseDTO;
import com.epam.xstack.models.entity.User;
import com.epam.xstack.models.enums.Code;
import com.epam.xstack.repository.UserRepository;
import com.epam.xstack.service.authentication_service.AuthenticationService;
import com.epam.xstack.token.Token;
import com.epam.xstack.token.TokenRepository;
import com.epam.xstack.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Profile(value = {"local", "dev", "prod", "stg"})
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordUserNameGenerator generator;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationChangeLoginRequestMapper requestMapper;
    private final UserNameCorrectValidation userNameValidation;

    private final Map<String, Integer> failedLoginAttempts = new ConcurrentHashMap<>();
    private final Map<String, Long> blockedUsers = new ConcurrentHashMap<>();

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {

        if (isUserBlocked(request.getUserName())) {
            throw new RuntimeException("User is blocked due to too many unsuccessful login attempts.");
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(),
                            request.getPassword()
                    )
            );
            var traineeInDb = repository.findByUserName(request.getUserName()).orElseThrow();
            var jwtToken = jwtService.generateToken(traineeInDb);
            var refreshToken = jwtService.generateRefreshToken(traineeInDb);
            revokeAllUserTokens(traineeInDb);
            saveUserToken(traineeInDb, jwtToken);
            resetFailedLoginAttempts(request.getUserName());
            return AuthenticationResponseDTO.builder()
                    .data("You are authenticated as user with username: " + traineeInDb.getUsername())
                    .code(Code.STATUS_200_OK)
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (AuthenticationException e) {
            incrementFailedLoginAttempts(request.getUserName());
            throw e;
        }
    }

    @Override
    @AuthenticationChangeLoginAspectAnnotation
    public AuthenticationChangeLoginResponseDTO authenticationChangeLogin(UUID id, AuthenticationChangeLoginRequestDTO requestDTO) {
        User userToBeUpdated = repository.findById(id).get();
        User user = requestMapper.toEntity(requestDTO);
        String generatedPassword = generator.generateRandomPassword();

        if (!userToBeUpdated.getPassword().equals(user.getPassword())) {
            userToBeUpdated.setUserName(requestDTO.getUserName());
            userToBeUpdated.setPassword(requestDTO.getNewPassword());
            userToBeUpdated.setPassword(passwordEncoder.encode(generatedPassword));

            repository.save(userToBeUpdated);
            requestMapper.toDto(user);
            return AuthenticationChangeLoginResponseDTO
                    .builder()
                    .response("Login and password changed")
                    .code(Code.STATUS_200_OK)
                    .build();
        } else {
            throw UserNameOrPasswordNotCorrectException
                    .builder()
                    .codeStatus(Code.USER_NOT_FOUND)
                    .message("User not exists in database")
                    .httpStatus(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }

    private boolean isUserBlocked(String username) {
        Long blockExpirationTime = blockedUsers.get(username);
        if (blockExpirationTime != null && blockExpirationTime > System.currentTimeMillis()) {
            return true;
        }
        return false;
    }

    private void incrementFailedLoginAttempts(String username) {
        int attempts = failedLoginAttempts.getOrDefault(username, 0) + 1;
        failedLoginAttempts.put(username, attempts);
        if (attempts >= 3) {
            long blockExpirationTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);
            blockedUsers.put(username, blockExpirationTime);
            failedLoginAttempts.remove(username);
            userNameValidation.userNameExists(username);
        }
    }

    private void resetFailedLoginAttempts(String username) {
        failedLoginAttempts.remove(username);
        blockedUsers.remove(username);
    }

    private void saveUserToken(com.epam.xstack.models.entity.User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByUserName(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponseDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}

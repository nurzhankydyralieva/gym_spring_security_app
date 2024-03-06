package com.epam.xstack.service.authentication_service.impl;


import com.epam.xstack.configuration.JwtService;
import com.epam.xstack.exceptions.exception.UserNameOrPasswordNotCorrectException;
import com.epam.xstack.exceptions.generator.PasswordUserNameGenerator;
import com.epam.xstack.exceptions.validator.UserNameCorrectValidation;
import com.epam.xstack.mapper.authentication_mapper.AuthenticationChangeLoginRequestMapper;
import com.epam.xstack.models.dto.authentication_dto.AuthenticationChangeLoginRequestDTO;
import com.epam.xstack.models.dto.authentication_dto.AuthenticationChangeLoginResponseDTO;
import com.epam.xstack.models.dto.authentication_dto.AuthenticationRequestDTO;
import com.epam.xstack.models.dto.authentication_dto.AuthenticationResponseDTO;
import com.epam.xstack.models.entity.Trainee;
import com.epam.xstack.models.entity.User;
import com.epam.xstack.models.enums.Code;
import com.epam.xstack.repository.UserRepository;
import com.epam.xstack.token.TokenRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AuthenticationServiceImplTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository repository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private PasswordUserNameGenerator generator;
    @Mock
    private UserNameCorrectValidation userNameValidation;
    @Mock
    private AuthenticationChangeLoginRequestMapper requestMapper;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    public void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        requestMapper = mock(AuthenticationChangeLoginRequestMapper.class);
        userRepository = mock(UserRepository.class);
        jwtService = mock(JwtService.class);
        generator = mock(PasswordUserNameGenerator.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authenticationService = new AuthenticationServiceImpl(repository, tokenRepository, passwordEncoder, generator,
                jwtService, authenticationManager, requestMapper, userNameValidation);
    }

    @Test
    public void testShouldChangeLoginAuthentication() {
        UUID id = UUID.randomUUID();
        String newPassword = "password";
        User userToBeUpdated = new User();
        userToBeUpdated.setPassword(newPassword);
        when(userRepository.findById(id)).thenReturn(Optional.of(userToBeUpdated));
        when(generator.generateRandomPassword()).thenReturn(newPassword);

        AuthenticationChangeLoginRequestDTO requestDTO = new AuthenticationChangeLoginRequestDTO();
        when(requestMapper.toEntity(requestDTO)).thenReturn(userToBeUpdated);

        AuthenticationChangeLoginResponseDTO responseDTO = new AuthenticationChangeLoginResponseDTO();
        assertThrows(UserNameOrPasswordNotCorrectException.class,
                () -> authenticationService.authenticationChangeLogin(id, requestDTO));
        assertNotNull(responseDTO);
        verify(userRepository, never()).save(any(User.class));
        verify(requestMapper, never()).toDto(any(User.class));
    }


    @Test
    public void testShouldAuthenticateUser() {
        AuthenticationRequestDTO request = new AuthenticationRequestDTO();
        request.setUserName("Marlin.Monro");
        request.setPassword("password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword());
        when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
        Trainee traineeInDb = new Trainee();
        traineeInDb.setUserName(request.getUserName());
        when(userRepository.findByUserName(request.getUserName())).thenReturn(Optional.of(traineeInDb));
        String jwtToken = "jwtToken";
        when(jwtService.generateToken(traineeInDb)).thenReturn(jwtToken);

        AuthenticationResponseDTO response = authenticationService.authenticate(request);

        assertNotNull(response);
        assertEquals(Code.STATUS_200_OK, response.getCode());
        assertEquals("You are authenticated as user with username: " + traineeInDb.getUsername(), response.getData());
    }
}

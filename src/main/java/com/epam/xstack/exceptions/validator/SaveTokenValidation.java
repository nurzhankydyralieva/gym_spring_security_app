package com.epam.xstack.exceptions.validator;

import com.epam.xstack.models.entity.User;
import com.epam.xstack.token.Token;
import com.epam.xstack.token.TokenRepository;
import com.epam.xstack.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveTokenValidation {
    private final TokenRepository tokenRepository;

    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}

package com.example.config.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
class AuthServiceBasic extends AuthService {

    private final String username;
    private final String password;

    AuthServiceBasic(@Value("${basic.username}") String username, @Value("${basic.password}") String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<Authentication> authenticate(HttpServletRequest request) {
        return extractBasicAuthHeader(request).flatMap(this::check);
    }

    private Optional<Authentication> check(Credentials credentials) {
        try {
            if (credentials.username().equals(this.username)) {
                if (this.password.equals(credentials.password())) {
                    Authentication authentication = createAuthentication(credentials.username(), AppRoles.USER);
                    return Optional.of(authentication);
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            log.warn("Unknown error while trying to check Basic Auth credentials", e);
            return Optional.empty();
        }
    }

}

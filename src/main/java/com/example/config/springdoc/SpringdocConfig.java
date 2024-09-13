package com.example.config.springdoc;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        name = "Basic",
        scheme = "basic")
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        name = "JWT",
        scheme = "bearer",
        bearerFormat = "JWT")
@SecurityScheme(
        type = SecuritySchemeType.OAUTH2,
        name = "OAuth2",
        flows = @OAuthFlows(
                password = @OAuthFlow(tokenUrl = "/api/auth/login",
                                      scopes = {
                                              @OAuthScope(name = "read_account", description = "read account"),
                                              @OAuthScope(name = "write_pets", description = "modify pets in your account")})
        ))
public class SpringdocConfig {
}

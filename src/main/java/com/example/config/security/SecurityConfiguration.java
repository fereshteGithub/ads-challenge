package com.example.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CorsFilter corsFilter;
    private final List<AuthService> authServices;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf().disable()
                .addFilterAt(this::authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                // Auth on all endpoints
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api-docs/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/auth/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/main/ping")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/main/time")).permitAll()
                //.requestMatchers(new AntPathRequestMatcher("/api/test/*")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/main/rest-test")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                .requestMatchers("/api/**").authenticated()
            .and()
                // Disable "JSESSIONID" cookies
                .sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Exception handling
                .exceptionHandling(conf -> conf.authenticationEntryPoint(this::authenticationFailedHandler));
        return http.build();
        // @formatter:on
    }

    private void authenticationFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Optional<Authentication> authentication = this.authenticate((HttpServletRequest) request);
        authentication.ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        chain.doFilter(request, response);
    }

    private Optional<Authentication> authenticate(HttpServletRequest request) {
        for (AuthService authService : this.authServices) {
            Optional<Authentication> authentication = authService.authenticate(request);
            if (authentication.isPresent()) {
                return authentication;
            }
        }
        return Optional.empty();
    }

    private void authenticationFailedHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        // Trigger the browser to prompt for Basic Auth
//        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

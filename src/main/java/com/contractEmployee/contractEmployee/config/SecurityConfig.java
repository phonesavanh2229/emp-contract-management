package com.contractEmployee.contractEmployee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().access((authentication, context) -> {
                            String gatewayHeader = context.getRequest().getHeader("X-Gateway");
                            return "true".equals(gatewayHeader)
                                    ? new AuthorizationDecision(true)
                                    : new AuthorizationDecision(false);
                        })
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler())   // 403
                        .authenticationEntryPoint(customAuthEntryPoint())   // 401
                );

        return http.build();
    }

    // 403 Forbidden handler (Not via Gateway)
    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Map<String, Object> body = new HashMap<>();
            body.put("success", false);
            body.put("message", "Access denied: must access via Gateway");
            body.put("path", request.getRequestURI());

            new ObjectMapper().writeValue(response.getOutputStream(), body);
        };
    }

    // 401 Unauthorized handler (No/invalid token)
    @Bean
    public AuthenticationEntryPoint customAuthEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Map<String, Object> body = new HashMap<>();
            body.put("success", false);
            body.put("message", "Unauthorized: missing or invalid token");
            body.put("path", request.getRequestURI());

            new ObjectMapper().writeValue(response.getOutputStream(), body);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

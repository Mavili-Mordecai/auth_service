package com.locus_narrative.auth_service.infrastructure.config;

import com.locus_narrative.auth_service.infrastructure.JwtAuthenticationEntryPoint;
import com.locus_narrative.auth_service.infrastructure.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
        http
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/*/v3/api-docs").permitAll()
                        .requestMatchers(HttpMethod.DELETE).authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint));
        return http.build();
    }
}

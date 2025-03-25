package com.example.demo.secruity;

import com.example.demo.secruity.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class UserSecurity {  // Renamed from 'UserSecruity' to 'UserSecurity'

    @Autowired
    private JwtAuthenticationFilter jwtFilter; // Marked as private for better encapsulation

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/test").authenticated() // Require authentication for this endpoint
                        .anyRequest().permitAll() // Allow all other requests
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set session to stateless
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter before authentication filter
                .httpBasic(); // Enable basic authentication

        return http.build();
    }
}

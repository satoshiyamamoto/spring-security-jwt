package com.example.springsecurityjwt.service.security;

import com.example.springsecurityjwt.service.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
    return http.authorizeHttpRequests(
            authz ->
                authz
                    .mvcMatchers("/login")
                    .permitAll()
                    .mvcMatchers("/user/**")
                    .hasRole("USER")
                    .mvcMatchers("/admin/**")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated())
        .csrf(CsrfConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilter(jwtAuthenticationFilter)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

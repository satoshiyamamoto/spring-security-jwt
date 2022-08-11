package com.example.springsecurityjwt.service.security.jwt;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfiguration {

  @Bean
  public JwtAccessTokenService jwtAuthenticationProvider(
      JwtProperties props, UserDetailsService userDetailsService) {
    return new JwtAccessTokenService(props, userDetailsService);
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(
      AuthenticationManager authenticationManager,
      JwtAccessTokenService jwtAuthenticationProvider) {
    return new JwtAuthenticationFilter(authenticationManager, jwtAuthenticationProvider);
  }

  @Bean
  public JwtAuthorizationFilter jwtAuthorizationFilter(
      JwtAccessTokenService jwtAuthenticationProvider) {
    return new JwtAuthorizationFilter(jwtAuthenticationProvider);
  }
}

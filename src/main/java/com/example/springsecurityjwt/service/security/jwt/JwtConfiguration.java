package com.example.springsecurityjwt.service.security.jwt;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfiguration {

  @Bean
  public JwtAuthenticationProvider jwtAuthenticationProvider(JwtProperties props) {
    return new JwtAuthenticationProvider(
        props.getSecrets(), props.getIssuer(), props.getExpiresMills());
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(
      AuthenticationManager authenticationManager,
      JwtAuthenticationProvider jwtAuthenticationProvider) {
    return new JwtAuthenticationFilter(authenticationManager, jwtAuthenticationProvider);
  }

  @Bean
  public JwtAuthorizationFilter jwtAuthorizationFilter(
      JwtAuthenticationProvider jwtAuthenticationProvider) {
    return new JwtAuthorizationFilter(jwtAuthenticationProvider);
  }
}

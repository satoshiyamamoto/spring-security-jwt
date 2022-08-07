package com.example.springsecurityjwt.service.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtAuthenticationProvider jwtAuthenticationProvider;

  public JwtAuthenticationFilter(
      AuthenticationManager authenticationManager,
      JwtAuthenticationProvider jwtAuthenticationProvider) {
    super(authenticationManager);
    this.jwtAuthenticationProvider = jwtAuthenticationProvider;
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    val accessToken = jwtAuthenticationProvider.createAccessToken(authResult);

    response.setStatus(HttpStatus.OK.value());
    response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
  }
}

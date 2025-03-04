package com.example.springsecurityjwt.service.security.jwt;

import com.example.springsecurityjwt.service.security.user.GrantedAuthorityMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final JwtAccessTokenService jwtAuthenticationService;

  public JwtAuthenticationFilter(
      AuthenticationManager authenticationManager, JwtAccessTokenService jwtAuthenticationService) {
    super(authenticationManager);
    this.jwtAuthenticationService = jwtAuthenticationService;
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    val accessTokens =
        jwtAuthenticationService.createAccessTokens(
            authResult.getName(), GrantedAuthorityMapper.toStrings(authResult.getAuthorities()));

    response.setStatus(HttpStatus.OK.value());
    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    objectMapper.writeValue(response.getWriter(), accessTokens);
  }
}

package com.example.springsecurityjwt.service.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_SCHEME = "Bearer ";

  private final JwtAccessTokenService jwtAuthenticationProvider;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    val uri = request.getRequestURI();
    if (uri.equals("/login")) {
      filterChain.doFilter(request, response);
      return;
    }

    val bearerToken = getBearerToken(request);
    if (bearerToken.isEmpty()) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      val authenticationToken = new JwtAuthenticationToken(bearerToken.get());
      val authentication = jwtAuthenticationProvider.authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);
    } catch (AuthenticationException e) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
      objectMapper.writeValue(
          response.getWriter(), Error.unauthorized(e.getMessage(), request.getRequestURI()));
    }
  }

  private static Optional<String> getBearerToken(HttpServletRequest request) {
    val value = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (!StringUtils.startsWithIgnoreCase(value, AUTHORIZATION_SCHEME)) {
      return Optional.empty();
    }

    return Optional.of(value.substring(AUTHORIZATION_SCHEME.length()));
  }

  record Error(String timestamp, long status, String error, String message, String path) {

    static Error unauthorized(String message, String path) {
      return new Error(
          Instant.now().toString(),
          HttpStatus.UNAUTHORIZED.value(),
          HttpStatus.UNAUTHORIZED.getReasonPhrase(),
          message,
          path);
    }
  }
}

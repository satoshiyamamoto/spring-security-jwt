package com.example.springsecurityjwt.service.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_SCHEME = "Bearer ";

  private final JwtAuthenticationProvider jwtAuthenticationProvider;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getRequestURI().equals("/login") && request.getMethod().equals("POST")) {
      filterChain.doFilter(request, response);
      return;
    }

    val bearerToken = getBearerToken(request);
    if (bearerToken == null) {
      filterChain.doFilter(request, response);
      return;
    }

    val authentication = jwtAuthenticationProvider.authenticate(bearerToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }

  @Nullable
  private static String getBearerToken(HttpServletRequest request) {
    val value = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (!StringUtils.startsWithIgnoreCase(value, AUTHORIZATION_SCHEME)) {
      return null;
    }

    return value.substring(AUTHORIZATION_SCHEME.length());
  }
}

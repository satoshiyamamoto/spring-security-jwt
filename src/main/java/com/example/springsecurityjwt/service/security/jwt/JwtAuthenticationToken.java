package com.example.springsecurityjwt.service.security.jwt;

import java.util.Collection;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

  public JwtAuthenticationToken(Object credentials) {
    super(null, credentials, List.of());
  }

  public JwtAuthenticationToken(
      Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }
}

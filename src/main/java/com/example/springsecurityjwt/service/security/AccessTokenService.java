package com.example.springsecurityjwt.service.security;

import java.util.List;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

public interface AccessTokenService {

  AccessTokenResponse createAccessTokens(Authentication authentication)
      throws BadCredentialsException;

  AccessTokenResponse createAccessTokens(String subject, List<String> roles);

  String createAccessToken(String subject, List<String> roles);

  String createRefreshToken(String subject);
}

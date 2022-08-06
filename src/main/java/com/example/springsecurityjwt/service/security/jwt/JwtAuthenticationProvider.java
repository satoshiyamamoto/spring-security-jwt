package com.example.springsecurityjwt.service.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Duration;
import java.time.Instant;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider {

  private static final String SECRETS = "secrets";

  private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRETS);

  private static final long EXPIRES_MILLS = Duration.ofDays(1).toMillis();

  public String createAccessToken(Authentication auth) {
    val roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    return JWT.create()
        .withSubject(auth.getName())
        .withIssuedAt(Instant.now())
        .withExpiresAt(Instant.now().plusMillis(EXPIRES_MILLS))
        .withClaim("roles", roles)
        .sign(ALGORITHM);
  }

  public Authentication authenticate(String accessToken) {
    throw new UnsupportedOperationException();
  }
}

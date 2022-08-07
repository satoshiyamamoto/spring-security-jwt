package com.example.springsecurityjwt.service.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.springsecurityjwt.service.security.user.GrantedAuthorityMapper;
import java.time.Instant;
import lombok.val;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationProvider {

  private final Algorithm algorithm;

  private final String issuer;

  private final long expiresMills;

  public JwtAuthenticationProvider(String secrets, String issuer, long expiresMills) {
    this.algorithm = Algorithm.HMAC256(secrets);
    this.issuer = issuer;
    this.expiresMills = expiresMills;
  }

  public String createAccessToken(Authentication auth) {
    val roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    return JWT.create()
        .withSubject(auth.getName())
        .withIssuedAt(Instant.now())
        .withExpiresAt(Instant.now().plusMillis(expiresMills))
        .withClaim("roles", roles)
        .withIssuer(issuer)
        .sign(algorithm);
  }

  public Authentication authenticate(String accessToken) throws BadCredentialsException {
    try {
      val jwt = JWT.require(algorithm).build().verify(accessToken);
      val principal = jwt.getSubject();
      val authorities =
          GrantedAuthorityMapper.fromStrings(jwt.getClaim("roles").asList(String.class));

      return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    } catch (JWTVerificationException e) {
      throw new BadCredentialsException(e.getMessage());
    }
  }
}

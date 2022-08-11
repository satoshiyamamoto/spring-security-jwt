package com.example.springsecurityjwt.service.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.springsecurityjwt.service.security.AccessTokenResponse;
import com.example.springsecurityjwt.service.security.AccessTokenService;
import com.example.springsecurityjwt.service.security.user.GrantedAuthorityMapper;
import java.time.Instant;
import java.util.List;
import lombok.val;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public class JwtAccessTokenService implements AccessTokenService {

  private final Algorithm algorithm;

  private final String issuer;

  private final long expiresSec;

  private final long refreshExpiresSec;

  private final UserDetailsService userDetailsService;

  public JwtAccessTokenService(JwtProperties props, UserDetailsService userDetailsService) {
    this.algorithm = Algorithm.HMAC256(props.getSecrets());
    this.issuer = props.getIssuer();
    this.expiresSec = props.getExpiresSec();
    this.refreshExpiresSec = props.getRefreshExpiresSec();
    this.userDetailsService = userDetailsService;
  }

  @Override
  public AccessTokenResponse createAccessTokens(Authentication authentication)
      throws BadCredentialsException {
    val user = userDetailsService.loadUserByUsername(authentication.getName());

    return createAccessTokens(
        user.getUsername(), GrantedAuthorityMapper.toStrings(user.getAuthorities()));
  }

  @Override
  public AccessTokenResponse createAccessTokens(String subject, List<String> roles) {
    val accessToken = createAccessToken(subject, roles);
    val refreshToken = createRefreshToken(subject);

    return new AccessTokenResponse(accessToken, "Bearer", expiresSec, refreshToken);
  }

  @Override
  public String createAccessToken(String subject, List<String> roles) {
    return JWT.create()
        .withSubject(subject)
        .withIssuedAt(Instant.now())
        .withExpiresAt(Instant.now().plusSeconds(expiresSec))
        .withClaim("roles", roles)
        .withIssuer(issuer)
        .sign(algorithm);
  }

  public String createRefreshToken(String subject) {
    return JWT.create()
        .withSubject(subject)
        .withIssuedAt(Instant.now())
        .withExpiresAt(Instant.now().plusSeconds(refreshExpiresSec))
        .withIssuer(issuer)
        .sign(algorithm);
  }

  public Authentication authenticate(Authentication authentication) {
    try {
      val accessToken = (String) authentication.getCredentials();
      val jwt = JWT.require(algorithm).build().verify(accessToken);
      val principal = jwt.getSubject();
      val authorities =
          GrantedAuthorityMapper.fromStrings(jwt.getClaim("roles").asList(String.class));

      return new JwtAuthenticationToken(principal, "", authorities);
    } catch (JWTVerificationException e) {
      throw new BadCredentialsException(e.getMessage(), e);
    }
  }
}

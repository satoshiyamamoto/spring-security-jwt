package com.example.springsecurityjwt.service.security.jwt;

import java.time.Duration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("jwt")
public class JwtProperties {

  private String secrets = "secrets";

  private String issuer = "http://localhost:8080/login";

  private long expiresMills = Duration.ofDays(1).toMillis();
}

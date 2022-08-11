package com.example.springsecurityjwt.interfaces.http;

import com.example.springsecurityjwt.service.security.AccessTokenResponse;
import com.example.springsecurityjwt.service.security.AccessTokenService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiController {

  private final AccessTokenService accessTokenService;

  @GetMapping("/refresh")
  public AccessTokenResponse refreshToken(Authentication authentication) {
    return accessTokenService.createAccessTokens(authentication);
  }

  @GetMapping("/user")
  public Principal getUser(Principal principal) {
    return principal;
  }

  @GetMapping("/admin")
  public Principal getAdmin(Principal principal) {
    return principal;
  }
}

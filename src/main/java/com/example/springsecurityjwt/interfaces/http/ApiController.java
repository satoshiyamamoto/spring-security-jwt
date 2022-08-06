package com.example.springsecurityjwt.interfaces.http;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

  @GetMapping("/user")
  public Principal getUser(Principal principal) {
    return principal;
  }

  @GetMapping("/admin")
  public Principal getAdmin(Principal principal) {
    return principal;
  }
}

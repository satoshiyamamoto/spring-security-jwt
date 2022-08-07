package com.example.springsecurityjwt.service.security.user;

import com.example.springsecurityjwt.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsMapper {

  static UserDetails map(User user) {
    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .authorities(GrantedAuthorityMapper.fromRoles(user.getRoles()))
        .build();
  }
}

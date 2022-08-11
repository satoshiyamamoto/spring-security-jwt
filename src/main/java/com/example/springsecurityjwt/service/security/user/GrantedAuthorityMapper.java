package com.example.springsecurityjwt.service.security.user;

import com.example.springsecurityjwt.domain.entity.Role;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public interface GrantedAuthorityMapper {

  static Collection<? extends GrantedAuthority> fromRoles(Collection<? extends Role> roles) {
    return roles == null
        ? List.of()
        : roles.stream().map(Role::getName).map(SimpleGrantedAuthority::new).toList();
  }

  static Collection<? extends GrantedAuthority> fromStrings(Collection<String> roles) {
    return roles == null ? List.of() : roles.stream().map(SimpleGrantedAuthority::new).toList();
  }

  static List<String> toStrings(Collection<? extends GrantedAuthority> roles) {
    return roles == null ? List.of() : roles.stream().map(GrantedAuthority::getAuthority).toList();
  }
}

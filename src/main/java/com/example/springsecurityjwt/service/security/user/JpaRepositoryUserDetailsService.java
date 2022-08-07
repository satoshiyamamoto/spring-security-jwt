package com.example.springsecurityjwt.service.security.user;

import com.example.springsecurityjwt.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaRepositoryUserDetailsService implements UserDetailsService {

  private final UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository
        .findByUsername(username)
        .map(UserDetailsMapper::map)
        .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
  }
}

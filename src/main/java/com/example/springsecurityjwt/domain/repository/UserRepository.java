package com.example.springsecurityjwt.domain.repository;

import com.example.springsecurityjwt.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserRepository extends JpaRepository<User, Long>, UserDetailsService {

  Optional<User> findByUsername(String username);

  default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return findByUsername(username)
        .map(User::toUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
  }
}

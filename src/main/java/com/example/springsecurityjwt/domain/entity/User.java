package com.example.springsecurityjwt.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
public class User extends AuditingEntity {

  @Id @GeneratedValue private Long id;

  private String username;

  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Role> roles;

  @JsonIgnore
  public UserDetails toUserDetails() {
    return new org.springframework.security.core.userdetails.User(username, password, roles);
  }
}

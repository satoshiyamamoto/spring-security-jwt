package com.example.springsecurityjwt.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
public class User extends AuditingEntity {

  @Id @GeneratedValue private Long id;

  @Column(unique = true)
  private String username;

  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Role> roles;
}

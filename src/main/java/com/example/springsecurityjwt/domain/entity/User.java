package com.example.springsecurityjwt.domain.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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

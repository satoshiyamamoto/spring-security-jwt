package com.example.springsecurityjwt.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "roles")
@EqualsAndHashCode(callSuper = false)
public class Role extends AuditingEntity implements GrantedAuthority {

  @Id @GeneratedValue private Long id;

  private String name;

  @Override
  public String getAuthority() {
    return name;
  }
}

package com.studyazimut.models;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String username;
  private String password;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;
  
  public User(User user) {
    
    this.roles = user.getRoles();
    this.username = user.getUsername();
    this.id = user.getId();
    this.password = user.getPassword();
  }
  
  public User(String pavel, String password, String roleName) {
    
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public Set<Role> getRoles() {
    return roles;
  }
  
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
  
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  
}

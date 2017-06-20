package com.studyazimut.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long roleId;
  
  @Column(name = "role")
  private String role;
  
  public Role() {
  }
  
  public Role(String role) {
    this.role = role;
  }
  
  public long getRoleId() {
    return roleId;
  }
  
  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }
  
  public String getRole() {
    return role;
  }
  
  public void setRole(String role) {
    this.role = role;
  }
}
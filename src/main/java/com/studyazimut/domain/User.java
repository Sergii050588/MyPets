package com.studyazimut.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
  
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name="user_id")
  private long id;
  
  @Column(name="user_name", nullable=false)
  private String username;
  
  public User() {
  }
  
  public User(String username) {
    this.username = username;
  }
  
  public long getid() {
    return id;
  }
  
  public void setid(long id) {
    this.id = id;
  }
  
  public String getusername() {
    return username;
  }
  
  public void setusername(String username) {
    this.username = username;
  }
  
  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            '}';
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    
    User user = (User) o;
    
    if (id != user.id) return false;
    return username.equals(user.username);
  }
  
  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + username.hashCode();
    return result;
  }
}

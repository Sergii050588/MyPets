package com.studyazimut.domain;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="user_id")
  private long id;
  
  @Column(name="user_name", nullable=false)
  private String userName;
  
  public User() {
  }
  
  public User(String userName) {
    this.userName = userName;
  }
  
  public long getid() {
    return id;
  }
  
  public void setid(long id) {
    this.id = id;
  }
  
  public String getuserName() {
    return userName;
  }
  
  public void setuserName(String userName) {
    this.userName = userName;
  }
  
  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", userName='" + userName + '\'' +
            '}';
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    
    User user = (User) o;
    
    if (id != user.id) return false;
    return userName.equals(user.userName);
  }
  
  @Override
  public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + userName.hashCode();
    return result;
  }
  
}

package com.studyazimut.JUnit;

import com.studyazimut.models.Role;
import com.studyazimut.models.User;
import com.studyazimut.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UsersControllerTest {
  
  private long id;
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private Role role;
  
  @Before
  public void init() {
    userService.deleteAll();
    Role role = new Role("user");
    String roleName = role.getRole();
    User user = new User("Pavel", "password", roleName);
    userService.save(user);
    id = user.getId();
  }
  
  @Test
  public void getUser() {
    
  }
  
  @Test
  public void createUser() {
    
  }
}

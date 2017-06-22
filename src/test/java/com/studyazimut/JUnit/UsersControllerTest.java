package com.studyazimut.JUnit;

import com.studyazimut.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UsersControllerTest {
  
  private long id;
  
  @Autowired
  private UserRepository userRepository;
  
  @Test
  public void getUser() {
    
  }
  
  @Test
  public void createUser() {
    
  }
}

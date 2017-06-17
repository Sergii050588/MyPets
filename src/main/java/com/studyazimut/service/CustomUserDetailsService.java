package com.studyazimut.service;

import com.studyazimut.models.CustomUserDetails;
import com.studyazimut.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  
  @Autowired
  private UserService userService;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> optionalUsers = userService.findByUsername(username);
    
    optionalUsers
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    return optionalUsers
            .map(CustomUserDetails::new).get();
  }
}
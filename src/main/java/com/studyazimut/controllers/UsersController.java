package com.studyazimut.controllers;

import com.studyazimut.models.User;
import com.studyazimut.service.UserService;
import com.studyazimut.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class UsersController {
  
/*
User REST API does:

GET request to /api/user/1 returns the User with ID 1
POST request to /api/user with a User object as JSON creates a new User
*/
  
  public static final Logger logger = LoggerFactory.getLogger(PetsController.class);
  
  @Autowired
  private UserService userService;
  
  // -------------------Retrieve Single pet------------------------------------------
  
  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> getUser(@PathVariable("id") long id) {
    logger.info("Fetching User with id {}", id);
    User pet = userService.findOne(id);
    if (pet == null) {
      logger.error("User with id {} not found.", id);
      return new ResponseEntity(new CustomErrorType("User with id " + id
              + " not found"), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(pet, HttpStatus.OK);
  }
  
  // -------------------Create a User-------------------------------------------
  
  @RequestMapping(value = "/user", method = RequestMethod.POST)
  public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
    logger.info("Creating user : {}", user);
    
    userService.save(user);
    
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
    return new ResponseEntity<String>(headers, HttpStatus.CREATED);
  }

}

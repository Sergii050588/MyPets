package com.studyazimut.service;

import com.studyazimut.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserService extends CrudRepository<User, Long>  {
  Optional<User> findByUsername(String username);
}

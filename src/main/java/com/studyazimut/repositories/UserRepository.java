package com.studyazimut.repositories;

import com.studyazimut.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {}

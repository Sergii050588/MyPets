package com.studyazimut.repository;

import com.studyazimut.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
  User findByUserName(String username);

  @RestResource(exported = false)
  @Override
  void delete(Long aLong);
  
  @RestResource(exported = false)
  @Override
  void delete(User user);
  
  @RestResource(exported = false)
  @Override
  void delete(Iterable<? extends User> iterable);
  
  @RestResource(exported = false)
  @Override
  void deleteAll();
}


package com.studyazimut.controller;

import com.studyazimut.domain.Pet;
import com.studyazimut.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RepositoryRestController

public class PetController {
  
  @Autowired
  private PetRepository petRepository;
  
}

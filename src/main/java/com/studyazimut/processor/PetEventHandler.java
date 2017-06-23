package com.studyazimut.processor;

import com.studyazimut.domain.Pet;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class PetEventHandler {
  
  @HandleBeforeCreate
  public void setOwner(Pet pet) {
    
  }
  
}

package com.studyazimut.service;

import com.studyazimut.models.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetService extends CrudRepository<Pet, Long> {}

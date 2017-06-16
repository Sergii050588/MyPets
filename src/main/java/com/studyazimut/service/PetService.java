package com.studyazimut.service;

import com.studyazimut.models.Pet;

import java.util.List;

public interface PetService {
  
  Pet findById(long id);
  
  Pet findByName(String name);
  
  void savePet(Pet pet);
  
  void updatePet(Pet pet);
  
  void deletePetById(long id);
  
  List<Pet> findAllPets();
  
  boolean isPetExist(Pet pet);
  
}

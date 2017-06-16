package com.studyazimut.service;

import com.studyazimut.models.Pet;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("petService")
public class PetServiceImpl implements PetService {
  
  private static final AtomicLong counter = new AtomicLong();
  
  private static List<Pet> pets;
  
  public List<Pet> findAllPets() {
    return pets;
  }
  
  public Pet findById(long id) {
    for(Pet pet : pets){
      if(pet.getId() == id){
        return pet;
      }
    }
    return null;
  }
  
  public Pet findByName(String name) {
    for(Pet pet : pets){
      if(pet.getName().equalsIgnoreCase(name)){
        return pet;
      }
    }
    return null;
  }
  
  public void savePet(Pet pet) {
    pet.setId(counter.incrementAndGet());
    pets.add(pet);
  }
  
  public void updatePet(Pet pet) {
    int index = pets.indexOf(pet);
    pets.set(index, pet);
  }
  
  public void deletePetById(long id) {
    for (Iterator<Pet> iterator = pets.iterator(); iterator.hasNext(); ) {
      Pet pet = iterator.next();
      if (pet.getId() == id) {
        iterator.remove();
      }
    }
  }
  
  public boolean isPetExist(Pet pet) {
    return findByName(pet.getName())!=null;
  }
  
}

package com.studyazimut.controllers;

import com.studyazimut.models.Pet;
import com.studyazimut.service.PetService;
import com.studyazimut.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/*
Pet REST API does:

GET request to /api/pet returns a list of pets
GET request to /api/pet/1 returns the Pet with ID 1
POST request to /api/pet with a Pet object as JSON creates a new Pet
PUT request to /api/pet/3 with a Pet object as JSON updates the Pet with ID 3
*/

@RestController
@RequestMapping("/api")
public class PetsController {
  
  public static final Logger logger = LoggerFactory.getLogger(PetsController.class);
  
  @Autowired
  private PetService petService; //Service which will do all data retrieval/manipulation work
  
  // -------------------Retrieve All Pets---------------------------------------------
  
  @RequestMapping(value = "/pets", method = RequestMethod.GET)
  public ResponseEntity<List<Pet>> listAllPets() {
    List<Pet> pets = (List<Pet>) petService.findAll();
    if (pets.isEmpty()) {
      return new ResponseEntity(HttpStatus.NO_CONTENT);
      // You many decide to return HttpStatus.NOT_FOUND
    }
    return new ResponseEntity<List<Pet>>(pets, HttpStatus.OK);
  }
  
  
  // -------------------Retrieve Single pet------------------------------------------
  
  @RequestMapping(value = "/pet/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> getPet(@PathVariable("id") long id) {
    logger.info("Fetching pet with id {}", id);
    Pet pet = petService.findOne(id);
    if (pet == null) {
      logger.error("pet with id {} not found.", id);
      return new ResponseEntity(new CustomErrorType("pet with id " + id
              + " not found"), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(pet, HttpStatus.OK);
  }
  
  // -------------------Create a Pet-------------------------------------------
  
  @RequestMapping(value = "/pet", method = RequestMethod.POST)
  public ResponseEntity<?> createPet(@RequestBody Pet pet, UriComponentsBuilder ucBuilder) {
    logger.info("Creating pet : {}", pet);
    
    petService.save(pet);
    
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(ucBuilder.path("/api/pet/{id}").buildAndExpand(pet.getId()).toUri());
    return new ResponseEntity<String>(headers, HttpStatus.CREATED);
  }
  
  // ------------------- Update a pet ------------------------------------------------
  
  @RequestMapping(value = "/pet/{id}", method = RequestMethod.PUT)
  public ResponseEntity<?> updatePet(@PathVariable("id") long id, @RequestBody Pet pet) {
    logger.info("Updating pet with id {}", id);
  
    Pet currentPet = petService.findOne(id);
    
    if (currentPet == null) {
      logger.error("Unable to update. Pet with id {} not found.", id);
      return new ResponseEntity(new CustomErrorType("Unable to upate. Pet with id " + id + " not found."),
              HttpStatus.NOT_FOUND);
    }
    
    currentPet.setName(pet.getName());
  
    petService.save(currentPet);
    return new ResponseEntity<Pet>(currentPet, HttpStatus.OK);
  }
}

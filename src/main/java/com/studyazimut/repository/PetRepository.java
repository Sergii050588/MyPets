package com.studyazimut.repository;

import com.studyazimut.domain.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {
  
  @Query("select pet from Pet pet where pet.petType = 0")
  Page<Pet> findDogs(Pageable pageable);
  
  @Query("select pet from Pet pet where pet.petType = 1")
  Page<Pet> findCats(Pageable pageable);
}

package com.studyazimut.JUnit;

import com.studyazimut.controllers.PetsController;
import com.studyazimut.models.Pet;
import com.studyazimut.models.User;
import com.studyazimut.service.PetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PetsControllerTest {
  
  private long id;
  
  @Autowired
  PetService petService;
  
  @Before
  public void init() {
    petService.deleteAll();
    Pet pet1 = new Pet("Abrasha");
    Pet pet2 = new Pet("Sara");
    petService.save(pet1);
    petService.save(pet2);
    id = pet1.getId();
  }
  
  @Test
  public void listAllPets() {
    
  }
  
  @Test
  public void getPet() {
    assertEquals("Pet(id=" + id + ", name=Abrasha)", petService.findOne(id).toString());
    assertNotEquals("Pet(id=" + id + ", name=Barasha)", petService.findOne(id).toString());
  }
  
  @Test
  public void createPet() {
    Pet burunduk = new Pet("Burunduk");
    petService.save(burunduk);
    long burundukId = burunduk.getId();
    assertEquals("Pet(id=" + burundukId + ", name=Burunduk)", petService.findOne(burundukId).toString());
  }
  
  @Test
  public void updatePet() {
    Pet abrasha = petService.findOne(id);
    abrasha.setName("Marat");
    petService.save(abrasha);
    assertEquals("Pet(id=" + id + ", name=Marat)", petService.findOne(id).toString());
  }
  
}

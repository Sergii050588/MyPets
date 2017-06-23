package com.studyazimut;

import com.studyazimut.domain.Pet;
import com.studyazimut.domain.User;
import com.studyazimut.infastructure.Admin;
import com.studyazimut.repository.PetRepository;
import com.studyazimut.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PetsHttpApiTests {
  
  @Autowired
  private MockMvc mockMvc;
  
  @Autowired
  private PetRepository petRepository;
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private UserDetailsService userDetailsService;
  
  private User referenceUser;
  
  @Before
  public void setup() {
    referenceUser = userRepository.findByUserName(Admin.ADMIN);
  }
  
  @Test
  public void createAd() throws Exception {
    Pet pet = pet();
    String requestBody = saveRequestJsonString(pet);
    
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
            .post("/pets")
            .accept(MediaTypes.HAL_JSON)
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(userDetailsService.loadUserByUsername(Admin.ADMIN))));
    
    final Pet createdBooking = findCreatedBooking();
    resultActions.andExpect(status().isCreated())
            .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/pets/" + createdBooking.getId()))
            .andExpect(jsonPath("$.type", is(pet.getType().name())))
            .andExpect(jsonPath("$.amount", is(pet.getAmount().intValue())))
            .andExpect(jsonPath("$.currency", is(pet.getCurrency().name())))
            .andExpect(jsonPath("$.rate", is(pet.getRate().doubleValue())))
            .andExpect(jsonPath("$.location.city", is(pet.getLocation().getCity())))
            .andExpect(jsonPath("$.location.area", is(pet.getLocation().getArea())))
            .andExpect(jsonPath("$.comment", is(pet.getComment())));
  }
  
  private Pet findCreatedBooking() {
    return petRepository.findAll(new Sort(Sort.Direction.DESC, "id")).iterator().next();
  }
  
  private Pet pet() {
    Pet pet = new Pet();
    pet.setType(pet.Type.BUY);
    pet.setAmount(BigInteger.valueOf(9999));
    pet.setCurrency(pet.Currency.USD);
    pet.setRate(BigDecimal.valueOf(21.5));
    pet.setLocation(new pet.Location("Kyiv", "Pechersk"));
    pet.setComment("need it now!");
    pet.setUser(referenceUser);
    return pet;
  }
  
  private static String saveRequestJsonString(pet pet) {
    return "{\n" +
            "  \"type\": \"" + pet.getType() + "\",\n" +
            "  \"amount\": " + pet.getAmount() + ",\n" +
            "  \"currency\": \"" + pet.getCurrency() + "\",\n" +
            "  \"rate\": " + pet.getRate() + ",\n" +
            "  \"location\": {\n" +
            "    \"city\": \"" + pet.getLocation().getCity() + "\",\n" +
            "    \"area\": \"" + pet.getLocation().getArea() + "\"\n" +
            "  },\n" +
            "  \"user\": \"/users/" + pet.getUser().getId() + "\",\n" +
            "  \"comment\": \"" + pet.getComment() + "\"\n" +
            "}";
  }
  
  
}

package com.studyazimut.Mockito;

import com.studyazimut.domain.Pet;
import com.studyazimut.domain.User;
import com.studyazimut.infastructure.Admin;
import com.studyazimut.repository.PetRepository;
import com.studyazimut.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs("src/docs/asciidoc")
public class PetsHttpApiWithDocsTests {
  
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
  public void createPet() throws Exception {
    Pet pet = pet();
    String requestBody = saveRequestJsonString(pet);
    
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
            .post("/pets")
            .accept(MediaTypes.HAL_JSON)
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(userDetailsService.loadUserByUsername(Admin.ADMIN))));
    
    final Pet createdPets = findPets();
    resultActions.andExpect(status().isCreated())
            .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost:8080/pets/" + createdPets.getId()))
            .andExpect(jsonPath("$.petType", is(createdPets.getPetType().name())))
            .andExpect(jsonPath("$.petName", is(createdPets.getPetName())));
    
    resultActions.andDo(document("create-pet",
            links(halLinks(),
                    linkWithRel("self").description("This pet"),
                    linkWithRel("pet").description("This <<pets, pet>>"),
                    linkWithRel("update").description("Updates this pet via PATCH")
                    
            ),
            responseFields(
                    fieldWithPath("_links").type(JsonFieldType.OBJECT).description("Links"),
                    fieldWithPath("petType").type(JsonFieldType.STRING).description("Type of the pet, one of: " +
                            Stream.of(Pet.Type.values()).map(Enum::name).collect(Collectors.joining(", "))),
                    fieldWithPath("petName").type(JsonFieldType.STRING).description("Pet name")
            )));
  }
  
  private Pet findPets() {
    return petRepository.findAll(new Sort(Sort.Direction.DESC, "id")).iterator().next();
  }
  
  private Pet pet() {
    Pet pet = new Pet();
    pet.setPetType(Pet.Type.CAT);
    pet.setPetName("Murzik");
    return pet;
  }
  
  private static String saveRequestJsonString(Pet pet) {
    return "{\n" +
            "  \"petType\": \"" + pet.getPetType() + "\",\n" +
            "  \"petName\": \"" + pet.getPetName() + "\"\n" +
            "}";
  }
  
}

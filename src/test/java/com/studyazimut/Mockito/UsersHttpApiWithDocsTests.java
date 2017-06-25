package com.studyazimut.Mockito;

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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
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
public class UsersHttpApiWithDocsTests {
  
  @Autowired
  private MockMvc mockMvc;
  
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
  public void createUsers() throws Exception {
    User users = users();
    String requestBody = saveRequestJsonString(users);
    
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
            .post("/users")
            .accept(MediaTypes.HAL_JSON)
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON)
            .with(user(userDetailsService.loadUserByUsername(Admin.ADMIN))));
    
    final User createdUser = findUsers();
    resultActions.andExpect(status().isCreated())
            .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost:8080/users/" + createdUser.getid()))
            .andExpect(jsonPath("$.userName", is(createdUser.getuserName())));
    
    resultActions.andDo(document("create-user",
            links(halLinks(),
                    linkWithRel("self").description("This user"),
                    linkWithRel("user").description("This <<users, user>>")
            
            ),
            responseFields(
                    fieldWithPath("_links").type(JsonFieldType.OBJECT).description("Links"),
                    fieldWithPath("userName").type(JsonFieldType.STRING).description("Sergey")
            )));
  }
  
  private User findUsers() {
    return userRepository.findAll(new Sort(Sort.Direction.DESC, "id")).iterator().next();
  }
  
  private User users() {
    User users = new User();
    users.setuserName("Sergey");
    return users;
  }
  
  private static String saveRequestJsonString(User users) {
    return "{\n" +
            "  \"userName\": \"" + users.getuserName() + "\"\n" +
            "}";
  }
  
}

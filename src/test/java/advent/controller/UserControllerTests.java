package advent.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
/*
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	
	@Test
	public void shouldCreateUser() throws Exception {
		String email = "dave.Kumar@gmail.com";
		String password = "dave2022";
		User newUser = new User(email, password);
		
		ResultActions resultActions = mockMvc.perform(put("/users")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(newUser))
			);
		
		resultActions.andExpect(status().isCreated());
		resultActions.andDo(print());
		resultActions.andExpect(jsonPath("id", notNullValue()));
		resultActions.andExpect(jsonPath("email", is(email)));
	}*/
}

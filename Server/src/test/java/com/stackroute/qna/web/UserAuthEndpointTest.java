package com.stackroute.qna.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.qna.TO.UserTO;
import com.stackroute.qna.exception.UserAlreadyExistsException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserAuthEndpoint.class)
public class UserAuthEndpointTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean	
	private UserService userService;
	
	private UserTO user;
	
	@Before
	public void init() {
		this.user = new UserTO();
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test.user@exmaple.com");
		user.setPassword("Test@pass1");
	}
	
	@Test
	public void whenLogin() {
		when(userService.login("test.user@exmaple.com", "Test@pass1"))
		.thenReturn("sample-token");
		
		try {
			mockMvc.perform(
					post("/qna/auth-api/v1/login")
					.param("email", "test.user@exmaple.com")
					.param("password", "Test@pass1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andDo(result ->{
				System.out.println(result.getResponse().getContentAsString());
				assertThat(result.getResponse().getContentAsString()).
				isEqualTo("sample-token");
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	@Test
	public void whenLoginFailed() {
		when(userService.login("test.user@exmaple.com", "Test@pass1"))
		.thenReturn(null);
		
		try {
			mockMvc.perform(
					post("/qna/auth-api/v1/login")
					.param("email", "test.user@exmaple.com")
					.param("password", "Test@pass1"))
			.andExpect(MockMvcResultMatchers.status().isUnauthorized());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	@Test
	public void whenSignup() throws UserAlreadyExistsException, UserNotFoundException {
		when(userService.addUser((UserTO)any()))
		.thenReturn(true);
		
		try {
			mockMvc.perform(
					post("/qna/auth-api/v1/signup")
					.contentType(MediaType.APPLICATION_JSON)
					.content(getJsonFromUserTO(this.user)))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andDo(result ->{
				assertThat(result.getResponse().getContentAsString()).
				isEqualTo("User signed up successfully with Email "+this.user.getEmail());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	@Test
	public void whenSignupUserAlreadyExistsException() throws UserAlreadyExistsException, UserNotFoundException {
		when(userService.addUser((UserTO)any()))
		.thenThrow(new UserAlreadyExistsException("User Already exists"));
		
		try {
			mockMvc.perform(
					post("/qna/auth-api/v1/signup")
					.contentType(MediaType.APPLICATION_JSON)
					.content(getJsonFromUserTO(this.user)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private static String getJsonFromUserTO(UserTO userTO) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(userTO);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	

}

package com.stackroute.qna.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.qna.TO.CommentTO;
import com.stackroute.qna.TO.CommentTO;
import com.stackroute.qna.TO.QuestionTO;
import com.stackroute.qna.TO.UserTO;
import com.stackroute.qna.exception.CommentNotFoundException;
import com.stackroute.qna.exception.QuestionNotFoundException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.service.CommentService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@WebMvcTest(CommentEndpoint.class)
public class CommentEndpointTest {
	

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean	
	private CommentService service;
	
	private List<CommentTO> comments;
	
	private String testUserEmail = "test.user@exmaple.com";
	

	@Before
	public void init() {
		QuestionTO question = new QuestionTO();
		question.setId(1);
		question.setDescription("Test Question 1 description");
		question.setCreatedOn(new Date());
		this.comments = new ArrayList<>();
		UserTO user = new UserTO();
		Set<CommentTO> comments = new HashSet<>();
		CommentTO comment = new CommentTO();
		comment.setDescription("Comment 1");
		comments.add(comment);
		CommentTO comment1 = new CommentTO();
		comment1.setDescription("Test Comment 1 description");
		comment1.setCreatedOn(new Date());
		comment1.setCreatedBy(user);
		comment1.setQuestion(question);
		
		
		this.comments.add(comment1);
	}
	
	@Test
	public void whenAddComment() throws CommentNotFoundException, QuestionNotFoundException, UserNotFoundException {
		when(service.addComment((CommentTO)any(), (String)any()))
		.thenReturn(true);
		
		try {
			mockMvc.perform(
					post("/qna/api/v1/comment")
					.header(HttpHeaders.AUTHORIZATION, generateToken(this.testUserEmail))
					.contentType(MediaType.APPLICATION_JSON)
					.content(getJsonFromCommentTO(this.comments.get(0))))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andDo(result ->{
				assertThat(result.getResponse().getContentAsString()).
				isEqualTo("true");
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void whenDeleteComment() throws CommentNotFoundException, UserNotFoundException {
		when(service.deleteComment(1))
		.thenReturn(true);
		
		try {
			mockMvc.perform(
					delete("/qna/api/v1/comment/1")
					.header(HttpHeaders.AUTHORIZATION, generateToken(this.testUserEmail)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andDo(result ->{
				assertThat(result.getResponse().getContentAsString()).
				isEqualTo("true");
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private static String generateToken(String email) {
		return "Bearer "+Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "my$ecr3tk3y")
				.compact();
	}
	
	
	private static List<CommentTO> getCommentTOfromJsonList(String json) {
		List<CommentTO> commentTOs = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			commentTOs = objectMapper.readValue(json, new TypeReference<List<CommentTO>>() {});
		} catch (IOException e) {
				e.printStackTrace();
		}
		return commentTOs;
	}
	
	private static CommentTO getCommentTOfromJson(String json) {
		CommentTO commentTO = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			commentTO = objectMapper.readValue(json, CommentTO.class);
		} catch (IOException e) {
				e.printStackTrace();
		}
		return commentTO;
	}
	
	private static String getJsonFromCommentTO(CommentTO commentTO) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(commentTO);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	

}

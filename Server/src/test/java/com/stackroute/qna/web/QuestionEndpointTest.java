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
import com.stackroute.qna.TO.QuestionTO;
import com.stackroute.qna.TO.TopicTO;
import com.stackroute.qna.TO.UserTO;
import com.stackroute.qna.exception.QuestionNotFoundException;
import com.stackroute.qna.exception.TopicNotFoundException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.service.QuestionService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@WebMvcTest(QuestionEndpoint.class)
public class QuestionEndpointTest {
	

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean	
	private QuestionService service;
	
	private List<QuestionTO> questions;
	
	private String testUserEmail = "test.user@exmaple.com";
	

	@Before
	public void init() {
		TopicTO topic = new TopicTO();
		topic.setId(1);
		topic.setName("Test Topic 1");
		topic.setDescription("Test Topic 1 description");
		topic.setCreatedOn(new Date());
		this.questions = new ArrayList<>();
		UserTO user = new UserTO();
		Set<CommentTO> comments = new HashSet<>();
		CommentTO comment = new CommentTO();
		comment.setDescription("Comment 1");
		comments.add(comment);
		QuestionTO question1 = new QuestionTO();
		question1.setDescription("Test Question 1 description");
		question1.setCreatedOn(new Date());
		question1.setCreatedBy(user);
		question1.setComments(comments);
		question1.setTopic(topic);
		
		
		this.questions.add(question1);
	}
	
	@Test
	public void whenGetQuestionDetails() throws QuestionNotFoundException {
		
		when(service.getQuestionDetails(1)).thenReturn(this.questions.get(0));
		try {
			mockMvc.perform(
					get("/qna/api/v1/question/1")
					.header(HttpHeaders.AUTHORIZATION, generateToken(this.testUserEmail)))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andDo(result ->{
						QuestionTO question  =  getQuestionTOfromJson(result.getResponse().getContentAsString());
						assertThat(question).isNotNull();
						assertThat(question.getDescription())
						.isEqualTo("Test Question 1 description");
						assertThat(question.getComments()).isNotNull();
						assertThat(question.getComments().size()).isEqualTo(1);
						assertThat(question.getComments().iterator().next().getDescription())
						.isEqualTo("Comment 1");
						
						
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void whenAddQuestion() throws QuestionNotFoundException, TopicNotFoundException, UserNotFoundException {
		when(service.addQuestion((QuestionTO)any(), (String)any()))
		.thenReturn(true);
		
		try {
			mockMvc.perform(
					post("/qna/api/v1/question")
					.header(HttpHeaders.AUTHORIZATION, generateToken(this.testUserEmail))
					.contentType(MediaType.APPLICATION_JSON)
					.content(getJsonFromQuestionTO(this.questions.get(0))))
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
	public void whenDeleteQuestion() throws QuestionNotFoundException, UserNotFoundException {
		when(service.deleteQuestion(1))
		.thenReturn(true);
		
		try {
			mockMvc.perform(
					delete("/qna/api/v1/question/1")
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
	
	
	private static List<QuestionTO> getQuestionTOfromJsonList(String json) {
		List<QuestionTO> questionTOs = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			questionTOs = objectMapper.readValue(json, new TypeReference<List<QuestionTO>>() {});
		} catch (IOException e) {
				e.printStackTrace();
		}
		return questionTOs;
	}
	
	private static QuestionTO getQuestionTOfromJson(String json) {
		QuestionTO questionTO = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			questionTO = objectMapper.readValue(json, QuestionTO.class);
		} catch (IOException e) {
				e.printStackTrace();
		}
		return questionTO;
	}
	
	private static String getJsonFromQuestionTO(QuestionTO questionTO) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(questionTO);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	

}

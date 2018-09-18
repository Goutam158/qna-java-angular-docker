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
import com.stackroute.qna.TO.QuestionTO;
import com.stackroute.qna.TO.TopicTO;
import com.stackroute.qna.TO.UserTO;
import com.stackroute.qna.exception.TopicNotFoundException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.service.TopicService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
@WebMvcTest(TopicEndpoint.class)
public class TopicEndpointTest {
	

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean	
	private TopicService service;
	
	private List<TopicTO> topics;
	
	private String testUserEmail = "test.user@exmaple.com";
	

	@Before
	public void init() {
		this.topics = new ArrayList<>();
		UserTO user = new UserTO();
		Set<QuestionTO> questions = new HashSet<>();
		QuestionTO question = new QuestionTO();
		question.setDescription("Question 1");
		questions.add(question);
		TopicTO topic1 = new TopicTO();
		topic1.setName("Test Topic 1");
		topic1.setDescription("Test Topic 1 description");
		topic1.setCreatedOn(new Date());
		topic1.setCreatedBy(user);
		topic1.setQuestions(questions);
		
		this.topics.add(topic1);
	}
	
	@Test
	public void whenGetAllTopics() {
		
		when(service.getAllTopics()).thenReturn(this.topics);
		try {
			mockMvc.perform(
					get("/qna/api/v1/topic")
					.header(HttpHeaders.AUTHORIZATION, generateToken(this.testUserEmail)))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andDo(result ->{
						List<TopicTO> topics  =  getTopicTOfromJsonList(result.getResponse().getContentAsString());
						assertThat(topics.size()).isEqualTo(1);
						assertThat(topics.get(0)).isNotNull();
						assertThat(topics.get(0).getName())
						.isEqualTo("Test Topic 1");
						assertThat(topics.get(0).getDescription())
						.isEqualTo("Test Topic 1 description");
						
						
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void whenGetTopicDetails() throws TopicNotFoundException {
		
		when(service.getTopicDetails(1)).thenReturn(this.topics.get(0));
		try {
			mockMvc.perform(
					get("/qna/api/v1/topic/1")
					.header(HttpHeaders.AUTHORIZATION, generateToken(this.testUserEmail)))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andDo(result ->{
						TopicTO topic  =  getTopicTOfromJson(result.getResponse().getContentAsString());
						assertThat(topic).isNotNull();
						assertThat(topic.getName())
						.isEqualTo("Test Topic 1");
						assertThat(topic.getDescription())
						.isEqualTo("Test Topic 1 description");
						assertThat(topic.getQuestions()).isNotNull();
						assertThat(topic.getQuestions().size()).isEqualTo(1);
						assertThat(topic.getQuestions().iterator().next().getDescription())
						.isEqualTo("Question 1");
						
						
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void whenAddTopic() throws TopicNotFoundException, UserNotFoundException {
		when(service.addTopic((TopicTO)any(), (String)any()))
		.thenReturn(true);
		
		try {
			mockMvc.perform(
					post("/qna/api/v1/topic")
					.header(HttpHeaders.AUTHORIZATION, generateToken(this.testUserEmail))
					.contentType(MediaType.APPLICATION_JSON)
					.content(getJsonFromTopicTO(this.topics.get(0))))
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
	public void whenDeleteTopic() throws TopicNotFoundException, UserNotFoundException {
		when(service.deleteTopic(1))
		.thenReturn(true);
		
		try {
			mockMvc.perform(
					delete("/qna/api/v1/topic/1")
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
	
	
	private static List<TopicTO> getTopicTOfromJsonList(String json) {
		List<TopicTO> topicTOs = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			topicTOs = objectMapper.readValue(json, new TypeReference<List<TopicTO>>() {});
		} catch (IOException e) {
				e.printStackTrace();
		}
		return topicTOs;
	}
	
	private static TopicTO getTopicTOfromJson(String json) {
		TopicTO topicTO = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			topicTO = objectMapper.readValue(json, TopicTO.class);
		} catch (IOException e) {
				e.printStackTrace();
		}
		return topicTO;
	}
	
	private static String getJsonFromTopicTO(TopicTO topicTO) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(topicTO);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	

}

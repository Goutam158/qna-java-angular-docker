package com.stackroute.qna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.qna.TO.CommentTO;
import com.stackroute.qna.TO.QuestionTO;
import com.stackroute.qna.TO.TopicTO;
import com.stackroute.qna.entity.CommentEntity;
import com.stackroute.qna.entity.QuestionEntity;
import com.stackroute.qna.entity.TopicEntity;
import com.stackroute.qna.entity.UserEntity;
import com.stackroute.qna.exception.QuestionNotFoundException;
import com.stackroute.qna.exception.TopicNotFoundException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.repository.QuestionRepository;
import com.stackroute.qna.repository.TopicRepository;
import com.stackroute.qna.repository.UserRepository;

@RunWith(SpringRunner.class)
public class QuestionServiceTest {
	
	@Mock
	private QuestionRepository questionRepository;
	
	@Mock
	private TopicRepository topicRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private QuestionService questionService;
	
	private QuestionEntity question;
	

	@Before
	public void init() {
		UserEntity user = new UserEntity();
		user.setId(1);
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test.user@exmaple.com");
		user.setPassword("Test@pass1");
		user.setCreated(new Date());
		
		CommentEntity comment1 = new CommentEntity();
		comment1.setCreatedBy(user);
		comment1.setDescription("Comment 1");
		comment1.setId(1);
		comment1.setCreatedOn(new Date());
		
		CommentEntity comment2 = new CommentEntity();
		comment2.setCreatedBy(user);
		comment2.setDescription("Comment 2");
		comment2.setId(2);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		comment2.setCreatedOn(cal.getTime());
		
		Set<CommentEntity> comments = new HashSet<>();
		comments.add(comment1);
		comments.add(comment2);
		
		TopicEntity topic = new TopicEntity();
		topic.setName("Topic 1");
		topic.setDescription("Topic 1 description");
		topic.setCreatedOn(new Date());
		topic.setId(1);
		topic.setCreatedBy(user);

		question = new QuestionEntity();
		question.setId(1);
		question.setDescription("Question 1");
		question.setCreatedOn(new Date());
		question.setComments(comments);
		question.setCreatedBy(user);
		question.setTopic(topic);
	}
	
	@Test
	public void whenGetQuestionDetails() throws QuestionNotFoundException {
		when(questionRepository.findOne(1)).thenReturn(this.question);
		QuestionTO question = this.questionService.getQuestionDetails(1);
		assertThat(question).isNotNull();
		assertThat(question.getDescription()).isEqualTo("Question 1");
		assertThat(question.getId()).isEqualTo(1);
		assertThat(question.getCreatedBy()).isNotNull();
		assertThat(question.getCreatedOn()).isNotNull();
		assertThat(question.getComments()).isNotNull();
		assertThat(question.getComments().size()).isEqualTo(2);
		CommentTO comment = question.getComments().iterator().next();
		assertThat(comment).isNotNull();
		assertThat(comment.getId()).isGreaterThan(0);
		assertThat(comment.getDescription()).startsWith("Comment");
		assertThat(comment.getCreatedOn()).isNotNull();
		assertThat(comment.getCreatedBy().getEmail()).isEqualTo("test.user@exmaple.com");

	}
	

	@Test
	public void whenGetQuestionDetailsQuestionNotFoundException(){
		when(questionRepository.findOne(1)).thenReturn(null);
		try {
			QuestionTO question = this.questionService.getQuestionDetails(1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(QuestionNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("Question not found for id 1");
		}
	}
	

	@Test
	public void whenAddQuestion() throws QuestionNotFoundException, UserNotFoundException, TopicNotFoundException {
		QuestionTO to = new QuestionTO();
		to.setDescription("Question 1 description");
		to.setCreatedOn(new Date());
		TopicTO topic = new TopicTO();
		topic.setId(1);
		to.setTopic(topic);

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.of(this.question.getCreatedBy()));
		
		when(topicRepository.findOne(to.getTopic().getId()))
		 .thenReturn(this.question.getTopic());

		when(questionRepository.save((QuestionEntity)any()))
		.thenReturn(this.question);

		assertThat(this.questionService.addQuestion(to , "test.user@exmaple.com"))
		.isTrue();
	}
	
	@Test
	public void whenAddQuestionReturnFalse() throws QuestionNotFoundException, UserNotFoundException, TopicNotFoundException {
		QuestionTO to = new QuestionTO();
		to.setDescription("Question 1 description");
		to.setCreatedOn(new Date());
		TopicTO topic = new TopicTO();
		topic.setId(1);
		to.setTopic(topic);

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.of(this.question.getCreatedBy()));
		
		when(topicRepository.findOne(to.getTopic().getId()))
		 .thenReturn(this.question.getTopic());

		QuestionEntity question = new QuestionEntity();
		question.setId(0);
		when(questionRepository.save((QuestionEntity)any()))
		.thenReturn(question);

		assertThat(this.questionService.addQuestion(to , "test.user@exmaple.com"))
		.isFalse();
	}
	
	@Test
	public void whenAddQuestionQuestionNotFoundException() {
		try {
			this.questionService.addQuestion(null, "test.user@exmaple.com");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(QuestionNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("Question is not proper");
		}
	}
	
	@Test
	public void whenAddQuestionUserNotFoundException() {
		QuestionTO to = new QuestionTO();
		to.setDescription("Question 1 description");
		to.setCreatedOn(new Date());
		TopicTO topic = new TopicTO();
		topic.setId(1);
		to.setTopic(topic);
		
		when(topicRepository.findOne(to.getTopic().getId()))
		 .thenReturn(this.question.getTopic());
		
		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(null));
		try {
			this.questionService.addQuestion(to, "test.user@exmaple.com");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("Logged in user could not be determined");
		}
	}
	
	@Test
	public void whenAddQuestionTopicNotFoundException() {
		QuestionTO to = new QuestionTO();
		to.setDescription("Question 1 description");
		to.setCreatedOn(new Date());
		to.setTopic(null);
		try {
			this.questionService.addQuestion(to, "test.user@exmaple.com");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(TopicNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("Reference Topic not found");
		}
	}
	

	@Test
	public void whenDeleteQuestion() throws QuestionNotFoundException {
		when(questionRepository.findOne(1))
		.thenReturn(this.question);
		assertThat(this.questionService.deleteQuestion(1))
		.isTrue();
		verify(this.questionRepository,times(1))
		.delete((QuestionEntity)any());
	}
	
	@Test
	public void whenDeleteQuestionException() {
		when(questionRepository.findOne(1))
		.thenReturn(null);
		try {
			this.questionService.deleteQuestion(1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(QuestionNotFoundException.class);
		}
	}


}

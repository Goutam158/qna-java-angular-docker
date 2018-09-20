package com.stackroute.qna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.qna.TO.QuestionTO;
import com.stackroute.qna.TO.TopicTO;
import com.stackroute.qna.entity.QuestionEntity;
import com.stackroute.qna.entity.TopicEntity;
import com.stackroute.qna.entity.UserEntity;
import com.stackroute.qna.exception.TopicNotFoundException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.repository.TopicRepository;
import com.stackroute.qna.repository.UserRepository;

@RunWith(SpringRunner.class)
public class TopicServiceTest {

	@Mock
	private Environment env; 
	
	@Mock
	private TopicRepository topicRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	TopicService topicService;

	private List<TopicEntity> topics;


	@Before
	public void init() {
		UserEntity user = new UserEntity();
		user.setId(1);
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test.user@exmaple.com");
		user.setPassword("Test@pass1");
		user.setCreated(new Date());

		Set<QuestionEntity> questions = new HashSet<>();

		QuestionEntity question1 = new QuestionEntity();
		question1.setId(1);
		question1.setDescription("Question 1");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		question1.setCreatedOn(cal.getTime());
		question1.setCreatedBy(user);

		QuestionEntity question2 = new QuestionEntity();
		question2.setId(2);
		question2.setDescription("Question 2");
		question2.setCreatedOn(new Date());
		question2.setCreatedBy(user);

		questions.add(question1);
		questions.add(question2);

		TopicEntity topic1 = new TopicEntity();
		topic1.setName("Topic 1");
		topic1.setDescription("Topic 1 description");
		topic1.setCreatedOn(cal.getTime());
		topic1.setId(1);
		topic1.setCreatedBy(user);
		topic1.setQuestions(questions);

		TopicEntity topic2 = new TopicEntity();
		topic2.setName("Topic 2");
		topic2.setDescription("Topic 2 description");
		topic2.setCreatedOn(new Date());
		topic2.setId(1);
		topic2.setCreatedBy(user);
		topic2.setQuestions(questions);

		this.topics =  new ArrayList<>();
		this.topics.add(topic1);
		this.topics.add(topic2);
	}

	@Test
	public void whenGetAllTopics() {
		when(topicRepository.findAll((Sort)any())).thenReturn(this.topics);
		List<TopicTO> topics = this.topicService.getAllTopics();
		assertThat(topics).isNotNull();
		assertThat(topics.size()).isEqualTo(2);
		TopicTO topic = topics.get(0);
		assertThat(topic).isNotNull();
		assertThat(topic.getName()).isEqualTo("Topic 1");
		assertThat(topic.getDescription()).isEqualTo("Topic 1 description");
		assertThat(topic.getId()).isEqualTo(1);
		assertThat(topic.getCreatedBy()).isNotNull();
		assertThat(topic.getCreatedOn()).isNotNull();
	}

	@Test
	public void whenGetTopicDetails() throws TopicNotFoundException {
		when(topicRepository.findOne(1)).thenReturn(this.topics.get(0));
		TopicTO topic = this.topicService.getTopicDetails(1);
		assertThat(topic).isNotNull();
		assertThat(topic.getName()).isEqualTo("Topic 1");
		assertThat(topic.getDescription()).isEqualTo("Topic 1 description");
		assertThat(topic.getId()).isEqualTo(1);
		assertThat(topic.getCreatedBy()).isNotNull();
		assertThat(topic.getCreatedOn()).isNotNull();
		assertThat(topic.getQuestions()).isNotNull();
		assertThat(topic.getQuestions().size()).isEqualTo(2);
		QuestionTO question = topic.getQuestions().iterator().next();
		assertThat(question).isNotNull();
		assertThat(question.getId()).isGreaterThan(0);
		assertThat(question.getDescription()).startsWith("Question");
		assertThat(question.getCreatedOn()).isNotNull();
		assertThat(question.getCreatedBy().getEmail()).isEqualTo("test.user@exmaple.com");

	}
	

	@Test
	public void whenGetTopicDetailsTopicNotFoundException(){
		when(topicRepository.findOne(1)).thenReturn(null);
		
		when(env.getProperty("qna.topic.not.found.with.id"))
		.thenReturn("Topic not found for id");
		
		try {
			TopicTO topic = this.topicService.getTopicDetails(1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(TopicNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("Topic not found for id 1");
		}
	}

	@Test
	public void whenAddTopic() throws TopicNotFoundException, UserNotFoundException {
		TopicTO to = new TopicTO();
		to.setName("Topic 1");
		to.setDescription("Topic 1 description");
		to.setCreatedOn(new Date());

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.of(this.topics.get(0).getCreatedBy()));

		when(topicRepository.save((TopicEntity)any()))
		.thenReturn(this.topics.get(0));

		assertThat(this.topicService.addTopic(to , "test.user@exmaple.com"))
		.isTrue();
	}
	
	@Test
	public void whenAddTopicReturnFalse() throws TopicNotFoundException, UserNotFoundException {
		TopicTO to = new TopicTO();
		to.setName("Topic 1");
		to.setDescription("Topic 1 description");
		to.setCreatedOn(new Date());

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.of(this.topics.get(0).getCreatedBy()));

		TopicEntity topic = new TopicEntity();
		topic.setId(0);
		when(topicRepository.save((TopicEntity)any()))
		.thenReturn(topic);

		assertThat(this.topicService.addTopic(to , "test.user@exmaple.com"))
		.isFalse();
	}
	
	@Test
	public void whenAddTopicTopicNotFoundException() {
		when(env.getProperty("qna.topic.not.proper"))
		.thenReturn("Topic is not proper");
		try {
			this.topicService.addTopic(null, "test.user@exmaple.com");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(TopicNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("Topic is not proper");
		}
	}
	
	@Test
	public void whenAddTopicUserNotFoundException() {
		TopicTO to = new TopicTO();
		to.setName("Topic 1");
		to.setDescription("Topic 1 description");
		to.setCreatedOn(new Date());
		
		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(null));
		
		when(env.getProperty("qna.user.connot.be.determined"))
		.thenReturn("Logged in user could not be determined");
		
		try {
			this.topicService.addTopic(to, "test.user@exmaple.com");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("Logged in user could not be determined");
		}
	}

	@Test
	public void whenDeleteTopic() throws TopicNotFoundException {
		when(topicRepository.findOne(1))
		.thenReturn(this.topics.get(0));
		assertThat(this.topicService.deleteTopic(1))
		.isTrue();
		verify(this.topicRepository,times(1))
		.delete((TopicEntity)any());
	}
	
	@Test
	public void whenDeleteTopicException() {
		when(topicRepository.findOne(1))
		.thenReturn(null);
		try {
			this.topicService.deleteTopic(1);
		} catch (TopicNotFoundException e) {
			assertThat(e).isInstanceOf(TopicNotFoundException.class);
		}
	}
}



package com.stackroute.qna;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.stackroute.qna.entity.TopicEntity;
import com.stackroute.qna.entity.UserEntity;
import com.stackroute.qna.repository.TopicRepository;
import com.stackroute.qna.repository.UserRepository;

@Component
public class DataInitializer {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private UserRepository userRepository;

	private UserEntity systemUser;
	
	@PostConstruct
	public void init() {
		intializeSystemUser();
		initilizeTopicData();

	}
	
	private void intializeSystemUser() {
		systemUser = userRepository.findOne(1);
		if(null==systemUser) {
			systemUser =  new UserEntity();
			systemUser.setFirstName(env.getProperty("qna.system.user.first.name"));
			systemUser.setLastName(env.getProperty("qna.system.user.last.name"));
			systemUser.setEmail(env.getProperty("qna.system.user.email.name"));
			systemUser.setPassword(env.getProperty("qna.system.password.first.name"));
			
			userRepository.save(systemUser);
		}
	}
	
	private void initilizeTopicData() {
		if(null==systemUser) {
			return;
		}
		for(int i=1; i<6 ;i++) {
			TopicEntity topic  = topicRepository.findOne(i);
			if(null==topic) {
				topic = new TopicEntity();
				topic.setCreatedBy(systemUser);
				topic.setCreatedOn(new Date());
				topic.setName(env.getProperty("qna.topic.name."+i));
				topic.setDescription(env.getProperty("qna.topic.description."+i));
				
				topicRepository.save(topic);
			}
		}
	}
}

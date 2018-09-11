package com.stackroute.qna.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.qna.TO.QuestionTO;
import com.stackroute.qna.TO.TopicTO;
import com.stackroute.qna.entity.TopicEntity;
import com.stackroute.qna.entity.UserEntity;
import com.stackroute.qna.exception.TopicNotFoundException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.repository.TopicRepository;
import com.stackroute.qna.repository.UserRepository;
import com.stackroute.qna.util.QnaUtil;

@Service
public class TopicService {

	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private UserRepository userRepository;

	public List<TopicTO> getAllTopics(){
		List<TopicTO> topics = new ArrayList<>();
		List<TopicEntity> topicEntityList = topicRepository.findAll();
		topicEntityList.forEach(t ->  topics.add(QnaUtil.getTOfromEntity(t)));
		return topics;
	}

	public boolean deleteTopic(int id) throws TopicNotFoundException {
		TopicEntity topic = topicRepository.findOne(id);
		if(null == topic) {
			throw new TopicNotFoundException("Topic not found for id "+id);
		}
		topicRepository.delete(topic);
		return true;
	}
	
	public boolean addTopic(TopicTO to, String email) throws TopicNotFoundException, UserNotFoundException {
		if(to==null) {
			throw new TopicNotFoundException("Topic is not proper");
		}
		Optional<UserEntity> user = userRepository.findByEmail(email);
		if(!user.isPresent()) {
			throw new UserNotFoundException("Logged in user could not be determined");
		}
		TopicEntity topic = QnaUtil.getEntityFromTO(to);
		topic.setCreatedBy(user.get());
		topic.setCreatedOn(new Date());
		topic = topicRepository.save(topic);
		if(topic.getId()>0) {
			return true;
		}
		return false;
	}

	public TopicTO getTopicDetails(int id) throws TopicNotFoundException {
		TopicEntity entity = topicRepository.findOne(id);
		if(null == entity) {
			throw new TopicNotFoundException("Topic not found for id "+id);
		}
		TopicTO to = QnaUtil.getTOfromEntity(entity);
		Set<QuestionTO> questions = new HashSet<>();
		entity.getQuestions()
		.forEach(
				q -> questions
				.add(QnaUtil.getTOfromEntity(q))
				);
		to.setQuestions(questions);
		return to;
	}

}

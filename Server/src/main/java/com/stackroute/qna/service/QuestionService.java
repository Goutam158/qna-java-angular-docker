package com.stackroute.qna.service;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.qna.TO.CommentTO;
import com.stackroute.qna.TO.QuestionTO;
import com.stackroute.qna.entity.QuestionEntity;
import com.stackroute.qna.entity.TopicEntity;
import com.stackroute.qna.entity.UserEntity;
import com.stackroute.qna.exception.QuestionNotFoundException;
import com.stackroute.qna.exception.TopicNotFoundException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.repository.QuestionRepository;
import com.stackroute.qna.repository.TopicRepository;
import com.stackroute.qna.repository.UserRepository;
import com.stackroute.qna.util.QnaUtil;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private UserRepository userRepository;

	public boolean deleteQuestion(int id) throws QuestionNotFoundException {
		QuestionEntity entity = questionRepository.findOne(id);
		if(null == entity) {
			throw new QuestionNotFoundException("Question not found for id "+id);
		}
		questionRepository.delete(entity);
		return true;
	}
	
	public boolean addQuestion(QuestionTO to, String email) throws QuestionNotFoundException, TopicNotFoundException , UserNotFoundException{
		
		if(to == null) {
			throw new QuestionNotFoundException("Question is not proper");
		}
		if(null == to.getTopic()) {
			throw new TopicNotFoundException("Reference Topic not found");
		}
		TopicEntity topic  = topicRepository.findOne(to.getTopic().getId());
		if(null == topic) {
			throw new TopicNotFoundException("Reference Topic not found");
		}
		Optional<UserEntity> user = userRepository.findByEmail(email);
		if(!user.isPresent()) {
			throw new UserNotFoundException("Logged in user could not be determined");
		}
		QuestionEntity question = QnaUtil.getEntityFromTO(to);
		question.setTopic(topic);
		question.setCreatedBy(user.get());
		question.setCreatedOn(new Date());
		question = questionRepository.save(question);
		if(question.getId()>0) {
			return true;
		}
		return false;
	}

	public QuestionTO getQuestionDetails(int id) throws QuestionNotFoundException {
		QuestionEntity entity = questionRepository.findOne(id);
		if(null == entity) {
			throw new QuestionNotFoundException("Question not found for id "+id);
		}
		QuestionTO to = QnaUtil.getTOfromEntity(entity);
		TreeSet<CommentTO> comments = new TreeSet<>(
			Comparator.comparing(CommentTO::getCreatedOn)
		);
		entity.getComments()
		.forEach(
				q -> comments
				.add(QnaUtil.getTOfromEntity(q))
				);
		to.setComments(comments.descendingSet());
		return to;
	}

}

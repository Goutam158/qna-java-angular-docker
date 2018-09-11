package com.stackroute.qna.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.qna.TO.CommentTO;
import com.stackroute.qna.TO.QuestionTO;
import com.stackroute.qna.entity.QuestionEntity;
import com.stackroute.qna.entity.TopicEntity;
import com.stackroute.qna.exception.QuestionNotFoundException;
import com.stackroute.qna.exception.TopicNotFoundException;
import com.stackroute.qna.repository.QuestionRepository;
import com.stackroute.qna.repository.TopicRepository;
import com.stackroute.qna.util.QnaUtil;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private TopicRepository topicRepository;

	public boolean deleteQuestion(int id) throws QuestionNotFoundException {
		QuestionEntity entity = questionRepository.findOne(id);
		if(null == entity) {
			throw new QuestionNotFoundException("Question not found for id "+id);
		}
		questionRepository.delete(entity);
		return true;
	}
	
	public boolean addQuestion(QuestionTO to) throws QuestionNotFoundException, TopicNotFoundException {
		
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
		QuestionEntity question = QnaUtil.getEntityFromTO(to);
		question.setTopic(topic);
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
		Set<CommentTO> comments = new HashSet<>();
		entity.getComments()
		.forEach(
				q -> comments
				.add(QnaUtil.getTOfromEntity(q))
				);
		to.setComments(comments);
		return to;
	}

}

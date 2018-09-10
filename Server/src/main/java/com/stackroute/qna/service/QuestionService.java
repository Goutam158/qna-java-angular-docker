package com.stackroute.qna.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.qna.TO.CommentTO;
import com.stackroute.qna.TO.QuestionTO;
import com.stackroute.qna.TO.TopicTO;
import com.stackroute.qna.entity.QuestionEntity;
import com.stackroute.qna.entity.TopicEntity;
import com.stackroute.qna.exception.QuestionNotFoundException;
import com.stackroute.qna.exception.TopicNotFoundException;
import com.stackroute.qna.repository.QuestionRepository;
import com.stackroute.qna.util.QnaUtil;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	public boolean deleteQuestion(int id) throws QuestionNotFoundException {
		QuestionEntity entity = questionRepository.findOne(id);
		if(null == entity) {
			throw new QuestionNotFoundException("Question not found for id "+id);
		}
		questionRepository.delete(entity);
		return true;
	}
	
	public boolean addQuestion(QuestionTO to) throws QuestionNotFoundException {
		
		if(to==null) {
			throw new QuestionNotFoundException("Question is not proper");
		}
		QuestionEntity entity = QnaUtil.getEntityFromTO(to);
		entity = questionRepository.save(entity);
		if(entity.getId()>0) {
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
		List<CommentTO> questions = new ArrayList<>();
		entity.getComments()
		.forEach(
				q -> questions
				.add(QnaUtil.getTOfromEntity(q))
				);
		return to;
	}

}

package com.stackroute.qna.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.stackroute.qna.TO.CommentTO;
import com.stackroute.qna.entity.CommentEntity;
import com.stackroute.qna.entity.QuestionEntity;
import com.stackroute.qna.entity.UserEntity;
import com.stackroute.qna.exception.CommentNotFoundException;
import com.stackroute.qna.exception.QuestionNotFoundException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.repository.CommentRepository;
import com.stackroute.qna.repository.QuestionRepository;
import com.stackroute.qna.repository.UserRepository;
import com.stackroute.qna.util.QnaUtil;

@Service
public class CommentService {


	@Autowired
	private Environment env; 
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean addComment(CommentTO to, String email) throws CommentNotFoundException, QuestionNotFoundException, UserNotFoundException {
		
		if(to==null) {
			throw new CommentNotFoundException(env.getProperty("qna.comment.not.proper"));
		}
		if(null == to.getQuestion()) {
			throw new QuestionNotFoundException(env.getProperty("qna.reference.question.not.found"));
		}
		QuestionEntity question = questionRepository.findOne(to.getQuestion().getId());
		if(null == question) {
			throw new QuestionNotFoundException(env.getProperty("qna.reference.question.not.found"));
		}
		Optional<UserEntity> user = userRepository.findByEmail(email);
		if(!user.isPresent()) {
			throw new UserNotFoundException(env.getProperty("qna.user.connot.be.determined"));
		}
		CommentEntity comment = QnaUtil.getEntityFromTO(to);
		comment.setQuestion(question);
		comment.setCreatedBy(user.get());
		comment.setCreatedOn(new Date());
		comment = commentRepository.save(comment);
		if(comment.getId()>0) {
			return true;
		}
		return false;
	}
	
	public boolean deleteComment(int id) throws CommentNotFoundException {
		CommentEntity entity = commentRepository.findOne(id);
		if(null == entity) {
			throw new CommentNotFoundException(env.getProperty("qna.comment.not.found.with.id")+" "+id);
		}
		commentRepository.delete(entity);
		return true;
	}

}

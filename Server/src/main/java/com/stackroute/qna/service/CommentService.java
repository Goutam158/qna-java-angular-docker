package com.stackroute.qna.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.qna.TO.CommentTO;
import com.stackroute.qna.entity.CommentEntity;
import com.stackroute.qna.entity.QuestionEntity;
import com.stackroute.qna.exception.CommentNotFoundException;
import com.stackroute.qna.exception.QuestionNotFoundException;
import com.stackroute.qna.repository.CommentRepository;
import com.stackroute.qna.repository.QuestionRepository;
import com.stackroute.qna.util.QnaUtil;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	public boolean addComment(CommentTO to) throws CommentNotFoundException, QuestionNotFoundException {
		
		if(to==null) {
			throw new CommentNotFoundException("Comment is not proper");
		}
		if(null == to.getQuestion()) {
			throw new QuestionNotFoundException("Reference question not found");
		}
		QuestionEntity question = questionRepository.getOne(to.getQuestion().getId());
		if(null == question) {
			throw new QuestionNotFoundException("Reference question not found");
		}
		CommentEntity comment = QnaUtil.getEntityFromTO(to);
		comment.setQuestion(question);
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
			throw new CommentNotFoundException("Comment not found for id "+id);
		}
		commentRepository.delete(entity);
		return true;
	}

}

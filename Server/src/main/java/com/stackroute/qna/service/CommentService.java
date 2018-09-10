package com.stackroute.qna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.qna.TO.CommentTO;
import com.stackroute.qna.entity.CommentEntity;
import com.stackroute.qna.exception.CommentNotFoundException;
import com.stackroute.qna.repository.CommentRepository;
import com.stackroute.qna.util.QnaUtil;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	public boolean addComment(CommentTO to) throws CommentNotFoundException {
		
		if(to==null) {
			throw new CommentNotFoundException("Comment is not proper");
		}
		CommentEntity entity = QnaUtil.getEntityFromTO(to);
		entity = commentRepository.save(entity);
		if(entity.getId()>0) {
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

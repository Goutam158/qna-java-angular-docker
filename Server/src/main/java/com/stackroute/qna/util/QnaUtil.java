package com.stackroute.qna.util;

import com.stackroute.qna.TO.CommentTO;
import com.stackroute.qna.TO.QuestionTO;
import com.stackroute.qna.TO.TopicTO;
import com.stackroute.qna.entity.CommentEntity;
import com.stackroute.qna.entity.QuestionEntity;
import com.stackroute.qna.entity.TopicEntity;

public class QnaUtil {
	
	//Topic
	public static TopicTO getTOfromEntity(TopicEntity entity) {
		if(null==entity) {
			return null;
		}
		TopicTO to = new TopicTO();
		to.setName(entity.getName());
		to.setId(entity.getId());
		to.setDescription(entity.getDescription());
		to.setCreatedOn(entity.getCreatedOn());
		
		return to;
	}

	public static TopicEntity getEntityFromTO(TopicTO to) {
		if(null==to) {
			return null;
		}

		TopicEntity entity = new TopicEntity();
		entity.setName(to.getName());
		entity.setId(to.getId());
		entity.setDescription(to.getDescription());
		entity.setCreatedOn(to.getCreatedOn());
		return entity;
	}
	
	//Question
	public static QuestionTO getTOfromEntity(QuestionEntity entity) {
		if(null==entity) {
			return null;
		}
		QuestionTO to = new QuestionTO();
		to.setId(entity.getId());
		to.setDescription(entity.getDescription());
		to.setCreatedOn(entity.getCreatedOn());
		
		return to;
	}

	public static QuestionEntity getEntityFromTO(QuestionTO to) {
		if(null==to) {
			return null;
		}

		QuestionEntity entity = new QuestionEntity();
		entity.setId(to.getId());
		entity.setDescription(to.getDescription());
		entity.setCreatedOn(to.getCreatedOn());
		return entity;
	}
	
	//Comment
	public static CommentTO getTOfromEntity(CommentEntity entity) {
		if(null==entity) {
			return null;
		}
		CommentTO to = new CommentTO();
		to.setId(entity.getId());
		to.setDescription(entity.getDescription());
		to.setCreatedOn(entity.getCreatedOn());
		
		return to;
	}

	public static CommentEntity getEntityFromTO(CommentTO to) {
		if(null==to) {
			return null;
		}

		CommentEntity entity = new CommentEntity();
		entity.setId(to.getId());
		entity.setDescription(to.getDescription());
		entity.setCreatedOn(to.getCreatedOn());
		return entity;
	}

}

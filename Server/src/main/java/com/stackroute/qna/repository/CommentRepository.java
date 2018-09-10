package com.stackroute.qna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.qna.entity.CommentEntity;

@Repository
public interface CommentRepository  extends JpaRepository<CommentEntity, Integer>{
	
}


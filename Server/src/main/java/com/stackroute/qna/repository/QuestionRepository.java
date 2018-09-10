package com.stackroute.qna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.qna.entity.QuestionEntity;

@Repository
public interface QuestionRepository  extends JpaRepository<QuestionEntity, Integer>{
	
}

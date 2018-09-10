package com.stackroute.qna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.qna.entity.TopicEntity;

@Repository
public interface TopicRepository  extends JpaRepository<TopicEntity, Integer>{
	
}


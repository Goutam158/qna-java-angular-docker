package com.stackroute.qna.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.qna.entity.UserEntity;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, Integer>{

	public UserEntity findByEmailAndPassword(String email, String password);

	public Optional<UserEntity> findByEmail(String email);
	
}


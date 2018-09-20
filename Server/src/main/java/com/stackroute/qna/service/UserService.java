package com.stackroute.qna.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.stackroute.qna.TO.UserTO;
import com.stackroute.qna.entity.UserEntity;
import com.stackroute.qna.exception.UserAlreadyExistsException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.repository.UserRepository;
import com.stackroute.qna.util.UserAuthUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Environment env;
	
	public String login(String email, String password) {
		String token = null;
		UserEntity user = userRepository.findByEmailAndPassword(email, password);
		if(null!=user) {
			token = UserAuthUtil.generateToken(user.getEmail());
		}
		return token;
	}
	
	public boolean addUser(UserTO newUser) throws UserAlreadyExistsException,UserNotFoundException {
		if(null==newUser) {
			throw new UserNotFoundException(env.getProperty("qna.user.cannot.be.null"));
		}
		Optional<UserEntity> userOpt =  userRepository.findByEmail(newUser.getEmail());
		if(userOpt.isPresent()) {
			throw new UserAlreadyExistsException(newUser.getEmail()+" "+env.getProperty("qna.email.already.exists"));
		}
		UserEntity user = UserAuthUtil.getEntityFromTO(newUser);
		user.setCreated(new Date());
		user = userRepository.save(user);
		if(null==user) {
			return false;
		}
		return true;
	}
	
	public boolean updateUser(UserTO userTO) throws UserNotFoundException {
		if(null==userTO) {
			throw new UserNotFoundException(env.getProperty("qna.user.cannot.be.null"));
		}
		Optional<UserEntity> userOpt =  userRepository.findByEmail(userTO.getEmail());
		if(!userOpt.isPresent()) {
			throw new UserNotFoundException(env.getProperty("qna.user.not.found.with.email")+" "+userTO.getEmail());
		}
		UserEntity user = userOpt.get();
		user = userRepository.save(user);
		if(null==user) {
			return false;
		}
		return true;
	}
	
	public boolean deleteUser(String email) throws UserNotFoundException {
		if(null==email) {
			throw new UserNotFoundException(env.getProperty("qna.user.cannot.be.null"));
		}
		Optional<UserEntity> userOpt =  userRepository.findByEmail(email);
		if(!userOpt.isPresent()) {
			throw new UserNotFoundException(env.getProperty("qna.user.not.found.with.email")+" "+email);
		}
		UserEntity user = userOpt.get();
		userRepository.delete(user);
		return true;
	}
}

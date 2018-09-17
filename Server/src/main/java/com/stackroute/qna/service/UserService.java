package com.stackroute.qna.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
			throw new UserNotFoundException("User cannot be null");
		}
		Optional<UserEntity> userOpt =  userRepository.findByEmail(newUser.getEmail());
		if(userOpt.isPresent()) {
			throw new UserAlreadyExistsException("User with email "+newUser.getEmail()+" Alerady Exists");
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
			throw new UserNotFoundException("User cannot be null");
		}
		Optional<UserEntity> userOpt =  userRepository.findByEmail(userTO.getEmail());
		if(!userOpt.isPresent()) {
			throw new UserNotFoundException("User with email "+userTO.getEmail()+" Not found");
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
			throw new UserNotFoundException("User cannot be null");
		}
		Optional<UserEntity> userOpt =  userRepository.findByEmail(email);
		if(!userOpt.isPresent()) {
			throw new UserNotFoundException("User with email "+email+" Not found");
		}
		UserEntity user = userOpt.get();
		userRepository.delete(user);
		return true;
	}
}

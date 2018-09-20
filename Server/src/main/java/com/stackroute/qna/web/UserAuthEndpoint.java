package com.stackroute.qna.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.qna.TO.UserTO;
import com.stackroute.qna.exception.UserAlreadyExistsException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/qna/auth-api/v1")
public class UserAuthEndpoint {

	@Autowired
	private UserService userService;
	
	@Autowired
	private Environment env;

	@PostMapping(value="/login", produces="text/html")
	public ResponseEntity<String> login(@RequestParam("email") String email, @RequestParam("password") String password) {
		String token = userService.login(email, password);
		if(null ==  token) {
			return new ResponseEntity<String>(token, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}

	@PostMapping(value="/signup", produces="text/html", consumes="application/json")
	public ResponseEntity<String> signup(@Valid @RequestBody UserTO newUser) {
		boolean status = false;
		if(null == newUser) {
			return new ResponseEntity<String>(env.getProperty("qna.user.cannot.be.null"), HttpStatus.BAD_REQUEST);
		}
		try {
			status = userService.addUser(newUser);
		} catch (UserAlreadyExistsException | UserNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if(status) {
			return new ResponseEntity<String>(env.getProperty("qna.signup.successful")+" "+newUser.getEmail(), HttpStatus.CREATED);
		}else {
			return new ResponseEntity<String>(env.getProperty("qna.signup.failed")+" "+newUser.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

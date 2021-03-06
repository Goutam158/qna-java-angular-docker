package com.stackroute.qna.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.qna.TO.QuestionTO;
import com.stackroute.qna.TO.TopicTO;
import com.stackroute.qna.exception.QuestionNotFoundException;
import com.stackroute.qna.exception.TopicNotFoundException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.service.QuestionService;

@RestController
@RequestMapping("/qna/api/v1/question")
@CrossOrigin
public class QuestionEndpoint {

	@Autowired
	private QuestionService service;
	
	@PostMapping(value="", consumes="application/json")
	public boolean addQuestion(@Valid @RequestBody QuestionTO to, HttpServletRequest request) throws QuestionNotFoundException, TopicNotFoundException, UserNotFoundException {
		String email = (String)request.getAttribute("email");
		return service.addQuestion(to, email);
	}
	
	@DeleteMapping(value="/{id}")
	public boolean deleteQuestion(@PathVariable("id") int id) throws QuestionNotFoundException{
		return service.deleteQuestion(id);
	}
	@GetMapping(value="/{id}", produces="application/json")
	public QuestionTO getQuestionDetails(@PathVariable("id") int id) throws QuestionNotFoundException {
		return service.getQuestionDetails(id);
	}
}

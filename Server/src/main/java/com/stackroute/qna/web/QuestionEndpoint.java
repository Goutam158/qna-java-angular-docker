package com.stackroute.qna.web;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.stackroute.qna.service.QuestionService;

@RestController
@RequestMapping("/qna/api/v1/question")
public class QuestionEndpoint {

	@Autowired
	private QuestionService service;
	
	@PostMapping(value="/", consumes="application/json")
	public boolean addQuestion(@RequestBody QuestionTO to) throws QuestionNotFoundException {
		return service.addQuestion(to);
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

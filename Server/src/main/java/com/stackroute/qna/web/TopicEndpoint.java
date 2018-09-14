package com.stackroute.qna.web;

import java.util.List;

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

import com.stackroute.qna.TO.TopicTO;
import com.stackroute.qna.exception.TopicNotFoundException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.service.TopicService;

@RestController
@RequestMapping("/qna/api/v1/topic")
@CrossOrigin
public class TopicEndpoint {

	@Autowired
	private TopicService service;
	
	@GetMapping(value="", produces="application/json")
	public List<TopicTO> getAllTopics(){
		return service.getAllTopics();
	}
	
	@PostMapping(value="", consumes="application/json")
	public boolean addTopic(@Valid @RequestBody TopicTO to, HttpServletRequest request) throws TopicNotFoundException, UserNotFoundException {
		String email = (String)request.getAttribute("email");
		return service.addTopic(to, email);
	}
	
	@DeleteMapping(value="/{id}")
	public boolean deleteTopic(@PathVariable("id") int id) throws TopicNotFoundException {
		return service.deleteTopic(id);
	}
	@GetMapping(value="/{id}", produces="application/json")
	public TopicTO getTopicDetails(@PathVariable("id") int id) throws TopicNotFoundException {
		return service.getTopicDetails(id);
	}
}

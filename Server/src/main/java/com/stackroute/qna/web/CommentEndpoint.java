package com.stackroute.qna.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.qna.TO.CommentTO;
import com.stackroute.qna.exception.CommentNotFoundException;
import com.stackroute.qna.exception.QuestionNotFoundException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.service.CommentService;

@RestController
@RequestMapping("/qna/api/v1/comment")
@CrossOrigin
public class CommentEndpoint {

	@Autowired
	private CommentService service;
	
	@PostMapping(value="", consumes="application/json")
	public boolean addComment(@Valid @RequestBody CommentTO to, HttpServletRequest request) throws CommentNotFoundException, QuestionNotFoundException, UserNotFoundException {
		String email = (String)request.getAttribute("email");
		return service.addComment(to,email);
	}
	
	@DeleteMapping(value="/{id}")
	public boolean deleteComment(@PathVariable("id") int id) throws CommentNotFoundException{
		return service.deleteComment(id);
	}
}

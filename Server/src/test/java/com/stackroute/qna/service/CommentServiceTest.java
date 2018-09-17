package com.stackroute.qna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.qna.TO.CommentTO;
import com.stackroute.qna.TO.CommentTO;
import com.stackroute.qna.TO.QuestionTO;
import com.stackroute.qna.entity.CommentEntity;
import com.stackroute.qna.entity.CommentEntity;
import com.stackroute.qna.entity.QuestionEntity;
import com.stackroute.qna.entity.UserEntity;
import com.stackroute.qna.exception.CommentNotFoundException;
import com.stackroute.qna.exception.QuestionNotFoundException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.repository.CommentRepository;
import com.stackroute.qna.repository.QuestionRepository;
import com.stackroute.qna.repository.UserRepository;

@RunWith(SpringRunner.class)
public class CommentServiceTest {
	
	@Mock
	private QuestionRepository questionRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private CommentRepository commentRepository;
	
	@InjectMocks
	private CommentService commentService;
	
	private CommentEntity comment;
	

	@Before
	public void init() {
		UserEntity user = new UserEntity();
		user.setId(1);
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test.user@exmaple.com");
		user.setPassword("Test@pass1");
		user.setCreated(new Date());
		
		QuestionEntity question = new QuestionEntity();
		question.setDescription("Question 1");
		question.setCreatedOn(new Date());
		question.setId(1);
		question.setCreatedBy(user);

		comment = new CommentEntity();
		comment.setId(1);
		comment.setDescription("Comment 1");
		comment.setCreatedOn(new Date());
		comment.setCreatedBy(user);
		comment.setQuestion(question);
	}
	
	@Test
	public void whenAddComment() throws CommentNotFoundException, UserNotFoundException, QuestionNotFoundException {
		CommentTO to = new CommentTO();
		to.setDescription("Comment 1 description");
		to.setCreatedOn(new Date());
		QuestionTO question = new QuestionTO();
		question.setId(1);
		to.setQuestion(question);

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.of(this.comment.getCreatedBy()));
		
		when(questionRepository.findOne(to.getQuestion().getId()))
		 .thenReturn(this.comment.getQuestion());

		when(commentRepository.save((CommentEntity)any()))
		.thenReturn(this.comment);

		assertThat(this.commentService.addComment(to , "test.user@exmaple.com"))
		.isTrue();
	}
	
	@Test
	public void whenAddCommentReturnFalse() throws CommentNotFoundException, UserNotFoundException, QuestionNotFoundException {
		CommentTO to = new CommentTO();
		to.setDescription("Comment 1 description");
		to.setCreatedOn(new Date());
		QuestionTO question = new QuestionTO();
		question.setId(1);
		to.setQuestion(question);

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.of(this.comment.getCreatedBy()));
		
		when(questionRepository.findOne(to.getQuestion().getId()))
		 .thenReturn(this.comment.getQuestion());

		CommentEntity comment = new CommentEntity();
		comment.setId(0);
		when(commentRepository.save((CommentEntity)any()))
		.thenReturn(comment);

		assertThat(this.commentService.addComment(to , "test.user@exmaple.com"))
		.isFalse();
	}
	
	@Test
	public void whenAddCommentCommentNotFoundException() {
		try {
			this.commentService.addComment(null, "test.user@exmaple.com");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(CommentNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("Comment is not proper");
		}
	}
	
	@Test
	public void whenAddCommentUserNotFoundException() {
		CommentTO to = new CommentTO();
		to.setDescription("Comment 1 description");
		to.setCreatedOn(new Date());
		QuestionTO question = new QuestionTO();
		question.setId(1);
		to.setQuestion(question);
		
		when(questionRepository.findOne(to.getQuestion().getId()))
		 .thenReturn(this.comment.getQuestion());
		
		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(null));
		try {
			this.commentService.addComment(to, "test.user@exmaple.com");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("Logged in user could not be determined");
		}
	}
	
	@Test
	public void whenAddCommentQuestionNotFoundException() {
		CommentTO to = new CommentTO();
		to.setDescription("Comment 1 description");
		to.setCreatedOn(new Date());
		to.setQuestion(null);
		try {
			this.commentService.addComment(to, "test.user@exmaple.com");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(QuestionNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("Reference Question not found");
		}
	}
	

	@Test
	public void whenDeleteComment() throws CommentNotFoundException {
		when(commentRepository.findOne(1))
		.thenReturn(this.comment);
		assertThat(this.commentService.deleteComment(1))
		.isTrue();
		verify(this.commentRepository,times(1))
		.delete((CommentEntity)any());
	}
	
	@Test
	public void whenDeleteCommentException() {
		when(commentRepository.findOne(1))
		.thenReturn(null);
		try {
			this.commentService.deleteComment(1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(CommentNotFoundException.class);
		}
	}


}

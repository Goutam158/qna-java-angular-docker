package com.stackroute.qna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.qna.TO.UserTO;
import com.stackroute.qna.entity.UserEntity;
import com.stackroute.qna.exception.UserAlreadyExistsException;
import com.stackroute.qna.exception.UserNotFoundException;
import com.stackroute.qna.repository.UserRepository;
import com.stackroute.qna.util.QnaUtil;
import com.stackroute.qna.util.UserAuthUtil;

@RunWith(SpringRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private UserEntity user;


	@Before
	public void init() {
		user = new UserEntity();
		user.setId(1);
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test.user@exmaple.com");
		user.setPassword("Test@pass1");
		user.setCreated(new Date());
	}
	
	@Test
	public void whenLogin() {
		when(userRepository.findByEmailAndPassword("test.user@exmaple.com", "Test@pass1"))
		.thenReturn(this.user);
		
		String token = userService.login("test.user@exmaple.com", "Test@pass1");
		assertThat(token)
		.isEqualTo(UserAuthUtil.generateToken("test.user@exmaple.com"));
	}
	
	@Test
	public void whenLoginError() {
		when(userRepository.findByEmailAndPassword("test.user@exmaple.com", "Test@pass1"))
		.thenReturn(null);
		
		String token = userService.login("test.user@exmaple.com", "Test@pass1");
		assertThat(token)
		.isNull();
	}

	@Test
	public void whenAdduser() throws UserAlreadyExistsException, UserNotFoundException {

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(null));
		
		when(userRepository.save((UserEntity)any())).thenReturn(this.user);

		UserTO user =  new UserTO();
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test.user@exmaple.com");
		user.setPassword("Test@pass1");
		user.setCreated(new Date());

		assertThat(userService.addUser(user))
		.isTrue();
		verify(userRepository,times(1))
		.save((UserEntity)any());
	}
	
	@Test
	public void whenAdduserReturnFalse() throws UserAlreadyExistsException, UserNotFoundException {

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(null));
		
		when(userRepository.save((UserEntity)any())).thenReturn(null);

		UserTO user =  new UserTO();
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test.user@exmaple.com");
		user.setPassword("Test@pass1");
		user.setCreated(new Date());

		assertThat(userService.addUser(user))
		.isFalse();
		verify(userRepository,times(1))
		.save((UserEntity)any());
	}

	@Test
	public void whenAdduserUserNotFoundException(){
		try {
			userService.addUser(null);
		}catch(Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("User cannot be null");
		}
	}
	
	@Test
	public void whenAdduserUserAlreadyExistsException(){
		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(this.user));

		UserTO user =  new UserTO();
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test.user@exmaple.com");
		user.setPassword("Test@pass1");
		user.setCreated(new Date());
		try {
			userService.addUser(user);
		}catch(Exception e) {
			assertThat(e).isInstanceOf(UserAlreadyExistsException.class);
			assertThat(e.getMessage()).isEqualTo("User with email "+user.getEmail()+" Alerady Exists");
		}
	}
	

	@Test
	public void whenUpdateUser() throws UserNotFoundException {

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(this.user));
		
		when(userRepository.save((UserEntity)any())).thenReturn(this.user);

		UserTO user =  new UserTO();
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test.user@exmaple.com");
		user.setPassword("Test@pass1");
		user.setCreated(new Date());

		assertThat(userService.updateUser(user))
		.isTrue();
		verify(userRepository,times(1))
		.save((UserEntity)any());
	}
	

	@Test
	public void whenUpdateUserReturnFalse() throws UserNotFoundException {

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(this.user));
		
		when(userRepository.save((UserEntity)any())).thenReturn(null);

		UserTO user =  new UserTO();
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test.user@exmaple.com");
		user.setPassword("Test@pass1");
		user.setCreated(new Date());

		assertThat(userService.updateUser(user))
		.isFalse();
		verify(userRepository,times(1))
		.save((UserEntity)any());
	}
	
	@Test
	public void whenUpdateUserUserNotFoundExceptionBlankUser(){
		try {
			userService.updateUser(null);
		}catch(Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("User cannot be null");
		}
	}
	
	@Test
	public void whenUpdateUserUserNotFoundException(){
		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(null));

		UserTO user =  new UserTO();
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test.user@exmaple.com");
		user.setPassword("Test@pass1");
		user.setCreated(new Date());
		try {
			userService.addUser(user);
		}catch(Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("User with email "+user.getEmail()+" Not found");
		}
	}
	
	@Test
	public void whenDeleteUser() throws UserNotFoundException {

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(this.user));
		
		assertThat(userService.deleteUser("test.user@exmaple.com"))
		.isTrue();
		verify(userRepository,times(1))
		.delete((UserEntity)any());
	}
	
	@Test
	public void whenDeleteUserUserNotFoundExceptionBlankUser(){
		try {
			userService.deleteUser(null);
		}catch(Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("User cannot be null");
		}
	}
	
	
	@Test
	public void whenDeleteUserUserNotFoundException(){
		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(null));
		try {
			userService.deleteUser("test.user@exmaple.com");
		}catch(Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("User with email "+user.getEmail()+" Not found");
		}
	}
	
	
	

}

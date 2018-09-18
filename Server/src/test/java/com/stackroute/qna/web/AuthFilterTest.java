package com.stackroute.qna.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RunWith(SpringRunner.class)
public class AuthFilterTest {
	
	private String testUserEmail = "test.user@exmaple.com";
	
	@Test
	public void WhenDoFilter() throws IOException, ServletException {
		MockHttpServletRequest request =  new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		request.addHeader(HttpHeaders.AUTHORIZATION, generateToken(testUserEmail));
		request.setMethod("GET");
		AuthFilter authFilter = new AuthFilter();
		authFilter.doFilter(request, response, chain);
		
		assertThat(response.getStatus())
		.isEqualTo(HttpStatus.OK.value());
		assertThat(request.getAttribute("email"))
		.isEqualTo(testUserEmail);
	}
	

	@Test
	public void WhenDoFilterOptionsMethod() throws IOException, ServletException {
		MockHttpServletRequest request =  new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		request.setMethod("OPTIONS");
		AuthFilter authFilter = new AuthFilter();
		authFilter.doFilter(request, response, chain);
		
		assertThat(response.getStatus())
		.isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void WhenDoFilterNoHeader() {
		MockHttpServletRequest request =  new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		request.setMethod("GET");
		AuthFilter authFilter = new AuthFilter();
		try {
			authFilter.doFilter(request, response, chain);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			assertThat(e.getMessage())
			.isEqualTo("Missing Authorization header starting with 'Bearer '");
		}
	}
	
	@Test
	public void WhenDoFilterWrongHeader() {
		MockHttpServletRequest request =  new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.addHeader(HttpHeaders.AUTHORIZATION, "wrong-header");
		MockFilterChain chain = new MockFilterChain();
		request.setMethod("GET");
		AuthFilter authFilter = new AuthFilter();
		try {
			authFilter.doFilter(request, response, chain);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			assertThat(e.getMessage())
			.isEqualTo("Missing Authorization header starting with 'Bearer '");
		}
	}
	
	@Test
	public void WhenDoFilterInvalidToken() {
		MockHttpServletRequest request =  new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.addHeader(HttpHeaders.AUTHORIZATION, generateToken(""));
		MockFilterChain chain = new MockFilterChain();
		request.setMethod("GET");
		AuthFilter authFilter = new AuthFilter();
		try {
			authFilter.doFilter(request, response, chain);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			assertThat(e.getMessage())
			.isEqualTo("No valid user found in the auth token");
		}
	}
	

	private static String generateToken(String email) {
		return "Bearer "+Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "my$ecr3tk3y")
				.compact();
	}

}

package com.stackroute.qna.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;

public class AuthFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest)req;
		final HttpServletResponse response = (HttpServletResponse) res;

		if("OPTIONS".equals(request.getMethod())) {
			response.setStatus(200);
			chain.doFilter(req, res);
		}else {

			final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if(null==authHeader || !authHeader.startsWith("Bearer ")) {
				throw new ServletException("Missing Authorization header starting with 'Bearer '");
			}

			final String token = authHeader.substring(7);
			final String userName = Jwts
					.parser()
					.setSigningKey("my$ecr3tk3y")
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
			if(StringUtils.isEmpty(userName)) {
				throw new ServletException("No valid user found in the auth token");
			}
			request.setAttribute("userName", userName);
			chain.doFilter(request, response);
		}
	}



}

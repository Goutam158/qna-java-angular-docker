package com.stackroute.qna.web;

import java.io.IOException;
import java.util.ResourceBundle;

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
	
	ResourceBundle applicationProperties = ResourceBundle.getBundle("application");

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
				throw new ServletException(applicationProperties.getString("qna.auth.filter.missing.token"));
			}

			final String token = authHeader.substring(7);
			final String email = Jwts
					.parser()
					.setSigningKey(applicationProperties.getString("qna.jwt.secrect.key"))
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
			if(StringUtils.isEmpty(email)) {
				throw new ServletException(applicationProperties.getString("qna.auth.filter.no.valid.user"));
			}
			request.setAttribute("email", email);
			chain.doFilter(request, response);
		}
	}



}

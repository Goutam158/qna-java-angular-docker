package com.stackroute.qna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.GenericFilterBean;

import com.stackroute.qna.web.AuthFilter;

@SpringBootApplication
public class QnAApp {
	
	@Bean
	public FilterRegistrationBean authFilter() {
		final FilterRegistrationBean authFilter =  new FilterRegistrationBean();
		authFilter.setFilter(new AuthFilter());
		authFilter.addUrlPatterns("/qna/app/v1/api/*");
		return authFilter;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(QnAApp.class, args);
	}
}

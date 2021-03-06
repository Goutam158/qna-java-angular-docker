package com.stackroute.qna.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	@Autowired
	Environment env;

	@Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
        		.select()
                .apis(RequestHandlerSelectors.basePackage("com.stackroute.qna.web"))
                .paths(regex("/qna/api/v1.*"))
                .build();
    }
     
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(env.getProperty("qna.swagger.title"))
                .description(env.getProperty("qna.swagger.description"))
                .termsOfServiceUrl(env.getProperty("qna.swagger.terms.of.service"))
                .contact(new Contact(
                		env.getProperty("qna.swagger.contact.name"),
                		env.getProperty("qna.swagger.contact.url"), 
                		env.getProperty("qna.swagger.contact.email")))
                .build();
    }
}

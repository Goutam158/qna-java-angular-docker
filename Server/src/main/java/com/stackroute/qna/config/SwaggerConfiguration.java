package com.stackroute.qna.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                .title("QnA Application REST API")
                .description("QnA Application REST API. Swagger documentation")
                .termsOfServiceUrl("https://gitlab-cts.stackroute.in/Dhiman.Talapatra/qnaV1-java-boilerplate")
                .contact(new Contact("Dhiman Talapatra", "https://gitlab-cts.stackroute.in/Dhiman.Talapatra", "dhiman.talapatra@cognizant.com"))
                .build();
    }
}

package com.Hrms.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

	/*
	 * //swagger without jwt token
	 * 
	 * @Bean public Docket api() { return new
	 * Docket(DocumentationType.SWAGGER_2).select()
	 * .apis(RequestHandlerSelectors.basePackage("com.Tcra.Hrms.controller")).paths(
	 * PathSelectors.any()) .build().apiInfo(apiInfo()); }
	 */

	@Bean
	public Docket api() {
		ParameterBuilder paramBuilder = new ParameterBuilder();
		List<Parameter> params = new ArrayList<>();
		paramBuilder.name("Authorization").modelRef(new ModelRef("string")).parameterType("header").required(false)
				.build();
		params.add(paramBuilder.build());
		return new Docket(DocumentationType.SWAGGER_2).groupName("user-api").useDefaultResponseMessages(false)
				.globalOperationParameters(params).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().apiInfo(apiInfo())
				.consumes(new HashSet<String>(Arrays.asList("application/json")))
				.produces(new HashSet<String>(Arrays.asList("application/json")));
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("HRMS BACK END REST API", "Some custom description of API.", "API REFERENCE",
				"Terms of service", new Contact("TCRA Developers", "www.tcra.go.tz", "developers@tcra.go.tz"),
				"License of API", "API license URL", Collections.emptyList());
	}

}
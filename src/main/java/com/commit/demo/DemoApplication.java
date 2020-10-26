package com.commit.demo;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan("com.commit")
@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
public class DemoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("TeamBuy")
				.description("commIT Project ")
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.termsOfServiceUrl("")
				.version("1.0.0")
				.build();
	}
	
	
	
	@Bean 
	public ModelMapper getModelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STANDARD);
		//mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		
		//mapper.getConfiguration().setPropertyCondition(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		
		return mapper;

	}
	
//	 @Bean
//	    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//	    public Logger logger(){
//	        return LoggerFactory.getLogger(injectionPoint.getMethodParameter().getContainingClass());
//	    }

	 
		@Bean(value = "myLogger")
		@Scope("prototype")
		Logger logger(InjectionPoint _injectionPoint) {
			String callingClass = "General";
			if (_injectionPoint != null) {
				if (_injectionPoint.getMember() != null) {
					callingClass = _injectionPoint.getMember()
							.getDeclaringClass()
							.getName();
				} else if (_injectionPoint.getField() != null) {
					callingClass = _injectionPoint.getField()
							.getDeclaringClass()
							.getName();
				} else if (_injectionPoint.getMethodParameter() != null) {
					callingClass = _injectionPoint.getMethodParameter()
							.getContainingClass()
							.getName();
				}
			}

			Logger myLogger = LoggerFactory.getLogger(callingClass);

			return myLogger;
		}



}

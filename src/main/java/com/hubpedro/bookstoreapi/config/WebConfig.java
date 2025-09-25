package com.hubpedro.bookstoreapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration public class WebConfig implements WebMvcConfigurer {

	private final LoggingInterceptor loggingInterceptor;

	public WebConfig(LoggingInterceptor loggingInterceptor) {
		this.loggingInterceptor = loggingInterceptor;
	}

	@Override public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggingInterceptor).addPathPatterns("/api/**");
	}

	@Override public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**").allowedOrigins("http://localhost:3000").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*")
		        .allowCredentials(true);
	}
}
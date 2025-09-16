package com.hubpedro.bookstoreapi.infra.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		return true;
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		Long startTime = (Long) request.getAttribute("startTime");
		if (startTime != null) {
			long endTime = System.currentTimeMillis();
			long executeTime = endTime - startTime;
			logger.info("Request handled: {} {} - Tempo gasto: {} ms", request.getMethod(), request.getRequestURI(),
					executeTime);
		}
	}
}
package com.java8.practices.advance.conf;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class CorsFilter implements Filter 
{
	@Value("${spring.security.cors.origins}")
	private String allowOrigin;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException 
	{
		log.debug("REQ_ID : " + getRequestId());
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		response.setHeader("Access-Control-Allow-Origin", allowOrigin);
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
		if(request.getMethod().equals(HttpMethod.OPTIONS.name()))
			response.setStatus(HttpStatus.NO_CONTENT.value());
		else
			chain.doFilter(req, res);
	}

	private static volatile Long requestId = 0l;
	private static SimpleDateFormat format = new SimpleDateFormat("yyMMddmm");

	private static synchronized final String getRequestId()
	{
		String id = null;
		try 
		{
			if(requestId.longValue() >= 100000000l || requestId.longValue()==0) 
				requestId = Long.parseLong((format.format(new Date())+ ("01")));
			id = requestId.toString();
			Thread.currentThread().setName(id);
			return id;
		}
		finally 
		{
			requestId++;
			id = null;
		}
	}
}

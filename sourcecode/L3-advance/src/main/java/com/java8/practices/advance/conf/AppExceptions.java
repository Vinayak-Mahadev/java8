package com.java8.practices.advance.conf;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.java8.practices.advance.exceptions.ApplicationException;
import com.java8.practices.advance.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class AppExceptions extends ResponseEntityExceptionHandler
{
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) 
	{
		return new ResponseEntity<Object>(
				"Access denied message here : " + ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<Object> handleResourceNotFoundException(ApplicationException ex, WebRequest request) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", ex.getMessage());
		map.put("error_code", ex.getErrorCode());

		//		return new ResponseEntity<Object>(				map, new HttpHeaders(), HttpStatus.NOT_FOUND);
		return handleExceptionInternal(ex, map, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "This should be application specific");
		map.put("error_code", HttpStatus.CONFLICT);
		map.put("details", ex.getMessage());
		return handleExceptionInternal(ex, map, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler(ServletException.class)
	public ResponseEntity<Object> handleException(ServletException e, WebRequest request) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", "This should be application specific");
		map.put("error_code", HttpStatus.INTERNAL_SERVER_ERROR);
		map.put("details", e.getMessage());
		map.put("invalid_url",  "Access denied message here : "+ e.getRootCause());
		return new ResponseEntity<Object>(map, new HttpHeaders(), HttpStatus.FORBIDDEN);
	}
}

package com.java8.practices.advance.exceptions;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(value = AccessLevel.PUBLIC)
public class ApplicationException extends Exception
{
	private static final long serialVersionUID = 1L;

	private String message;
	
	private Long errorCode;

	public ApplicationException() 
	{
		super();
	}

	public ApplicationException(Long errorCode, String message)
	{
		this(errorCode, message, null);
	}

	public ApplicationException(Long errorCode, String message, Throwable cause)
	{
		super(message, cause);
		this.message = message;
		this.errorCode = errorCode;
	}

}

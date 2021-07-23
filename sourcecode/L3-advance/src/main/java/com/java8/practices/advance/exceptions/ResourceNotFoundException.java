package com.java8.practices.advance.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends ApplicationException
{
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() 
	{
		super();
	}

	public ResourceNotFoundException(Long errorCode, String message, Throwable cause)
	{
		super(errorCode, message, cause);
	}

	public ResourceNotFoundException(Long errorCode, String message)
	{
		this(errorCode, message, null);
	}

}

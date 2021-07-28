package com.java8.practices.intermediate.threads.concurrency;

import java.io.Serializable;

public class Message implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private final boolean isDataAvailable;
	
	private Long id;
	
	private Object message;

	public Message(Object message)
	{
		this.isDataAvailable=true;
		this.message = message;
	}
	
	public Message(long id, Object message)
	{
		this(message);
		this.id = id;
	}

	public Message(boolean isDataAvailable)
	{
		this.isDataAvailable = false;
	}

	public Object getMessage() 
	{
		return message;
	}
	
	public Long getId() 
	{
		return id;
	}

	@Override
	public String toString() 
	{
		return "Message [message=" + message + "]";
	}

	@Override
	protected void finalize() throws Throwable 
	{
		try 
		{
			super.finalize();
		} 
		catch (Exception e) 
		{

		}
		finally 
		{
			message = null;
		}
	}
	
	public boolean isDataAvailable()
	{
		return isDataAvailable;
	}
}

package com.java8.practices.advance.common;

public class Utility 
{
	public static synchronized void setThreadId(long id)
	{
		Thread.currentThread().setName(id+"");
	}
}

package com.java8.practices.advance.common;

public class Utility 
{

	public static synchronized String getId() 
	{
		return Thread.currentThread().getName();
	}

}

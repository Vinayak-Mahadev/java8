package com.java8.practices.intermediate;

import com.java8.practices.intermediate.threads.ExecutorFrameWork;
import com.java8.practices.intermediate.threads.ThreadsConcepts;

public class App 
{
	public static void main(String[] args) 
	{
		ThreadsConcepts.sample();
		
		ExecutorFrameWork.newSingleThreadExecutor();
		
		ExecutorFrameWork.newFixedThreadPool();

	}
}

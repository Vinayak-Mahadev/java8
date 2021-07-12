package com.java8.practices.intermediate.threads;

public class ThreadsConcepts
{
	public static void sample()
	{
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("ThreadsConcepts : Thread & Runnable Implementation Example");
		System.out.println("---------------------------------------------------------------------------");
		
		Thread threadType = null;
		Thread runnableType = null;
		Thread java8StaticType = null;
		Thread java8ObjectType = null;
		try 
		{
			System.out.println("Main thread :: " + Thread.currentThread().getId() + " : " + Thread.currentThread().getName());
			threadType = new ThreadType("Vinayak-Mahadev");
			runnableType = new Thread(new RunnableType("Vinayak-Mahadev"), "RunnableType");
			java8StaticType = new Thread(ThreadsConcepts::print, "Java8StaticType");
			java8ObjectType = new Thread(new Java8ObjectType("Vinayak-Mahadev")::print, "Java8ObjectType");

			threadType.start();
			runnableType.start();
			java8StaticType.start();
			java8ObjectType.start();

		}
		catch (Exception e) 
		{

		}
		System.out.println("---------------------------------------------------------------------------");
	}

	public static class ThreadType extends Thread
	{
		private String name;

		public ThreadType(String name) 
		{
			super("ThreadType");
			this.name = name;
		}

		@Override
		public void run() 
		{
			println(name);			
		}
	}

	public static class RunnableType implements Runnable
	{
		private String name;

		public RunnableType(String name) 
		{
			this.name = name;
		}

		@Override
		public void run() 
		{
			println(name);			
		}

	}

	public static class Java8ObjectType
	{
		private String name;

		Java8ObjectType(String name)
		{
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void print()
		{
			println(name);
		}
	}

	public static void print()
	{
		String name = "Vinayak-Mahadev";
		println(name);
	}

	public static void println(String name)
	{
		try 
		{
			System.out.println("Name :: " + name + " : " +Thread.currentThread().getId() + " : " + Thread.currentThread().getName());
		}
		catch (Exception e) 
		{

		}
	}
}

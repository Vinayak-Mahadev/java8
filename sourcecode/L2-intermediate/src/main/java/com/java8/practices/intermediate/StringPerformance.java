package com.java8.practices.intermediate;

public class StringPerformance 
{

	public static void main(String[] args)
	{
		int count = 200000000;
		testStringBuilder(count);
		testStringBuffer(count);
		testString(count);

	}

	@SuppressWarnings("unused")
	static void testString(int count)
	{
		System.out.println("------------------------------------------------------------------------------");
		Long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			String s = new String("Dummy"+i);
		}
		
		Long end = System.currentTimeMillis() - start;
		System.out.println("String :: " + end);
		System.out.println("------------------------------------------------------------------------------");
	}
	
	@SuppressWarnings("unused")
	static void testStringBuffer(int count)
	{
		System.out.println("------------------------------------------------------------------------------");
		Long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			StringBuffer s = new StringBuffer("Dummy"+i);
		}
		
		Long end = System.currentTimeMillis() - start;
		System.out.println("StringBuffer :: " + end);
		System.out.println("------------------------------------------------------------------------------");
	}
	
	@SuppressWarnings("unused")
	static void testStringBuilder(int count)
	{
		System.out.println("------------------------------------------------------------------------------");
		Long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			StringBuilder s = new StringBuilder("Dummy"+i);
		}
		
		Long end = System.currentTimeMillis() - start;
		System.out.println("StringBuilder :: " + end);
		System.out.println("------------------------------------------------------------------------------");
	}
}

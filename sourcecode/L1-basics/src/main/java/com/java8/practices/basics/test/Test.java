package com.java8.practices.basics.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test 
{
	public static void main(String[] args) 
	{
		//		String str = "vinay,abc,raj,vinay,Ram,abc,mani";
		//		System.out.println(getMap(str));

		System.out.println("reverse the number : " + reverseTheNumber(12));
		
		
	}

	
	public static Map<String,Long> getMap(String str) 
	{
		Comparator<Entry<String,Long>> comparator = 
				(a,b) -> 
		{
			if(a != null && b != null && a.getValue().compareTo(b.getValue()) < 0)
				return 1;
			return -1;
		};
		//		SortedMap<String, Long> map = new TreeMap<String, Long>((a,b) -> a != null && b!= null && a.equals(b) ? 1 : -1);

		return Arrays
				.stream(str.split(","))
				.filter(s -> s!=null ? true : false)
				.collect(Collectors.groupingByConcurrent(Function.identity(), Collectors.counting()))
				.entrySet()
				.stream()
				.sorted(comparator)
				.collect(Collectors.toMap(
						Map.Entry::getKey, 
						Map.Entry::getValue, 
						(oldValue, newValue) -> oldValue, LinkedHashMap::new));
		//		return null;
	}

	public static int  reverseTheNumber(int number)
	{
		int reverse = 0;
		while(number != 0)   
		{  
			int remainder = number % 10;  
			reverse  = reverse * 10 + remainder;  
			number = number/10;  
		}  
		return reverse;
	}
}
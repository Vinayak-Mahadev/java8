package com.java8.practices.basics.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test 
{
	public static void main(String[] args) 
	{
		String str = "vinay,abc,raj,vinay,Ram,abc,mani";
		System.out.println(getMap(str));
	}

	public static Map<String,Long> getMap(String str) 
	{
		Comparator<String> comparator = (a,b) -> a != null && b!= null && a.equals(b) ? 1 : -1;
		//		SortedMap<String, Long> map = new TreeMap<String, Long>((a,b) -> a != null && b!= null && a.equals(b) ? 1 : -1);

		Arrays
		.stream(str.split(","))
		.filter(s -> s!=null ? true : false)
		.collect(Collectors.groupingByConcurrent(Function.identity(), Collectors.counting()))
		.entrySet()
		.stream()
		.sorted(Map.Entry.comparingByValue())
		.collect(Collectors.toMap(
				Map.Entry::getKey, 
				Map.Entry::getValue, 
				(oldValue, newValue) -> oldValue, LinkedHashMap::new));
		;
		return null;
	}
}
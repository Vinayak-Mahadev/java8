package com.java8.practices.basics.lamda;

import java.util.Optional;

public class OptionalConcepts 
{
	public static void main(String[] args) 
	{
		String[] str = new String[10];
		str[5] = "Vinayak-Mahadev";
		Optional<String> optional = null;

		for (String s : str) 
		{
			System.out.println("s -> " + s);
			optional = Optional.ofNullable(s);
			optional.ifPresent(System.out::println);
			System.out.println(optional.orElseGet(()->"'0'"));
			System.out.println(optional.isPresent());
			System.out.println(optional.filter(m -> m.equalsIgnoreCase(s)));
			System.out.println("-----------------------");
		}

	}
}

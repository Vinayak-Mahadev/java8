package com.java8.practices.basics.lamda;

import java.util.Arrays;
import java.util.List;

public class MethodReferencesConcepts {

	public void runSamples() 
	{
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("MethodReferencesConcepts : Functional Interface, Method reference and Lambda Example");
		System.out.println("---------------------------------------------------------------------------");

		Interfaces interfaces = System.out::println;
		interfaces.display("Method reference to a static method of a class – Class::staticMethod");


		Example example = new Example();
		interfaces = example::display;
		interfaces.display("Method reference to an instance method of an object – object::instanceMethod");


		InterfacesCons interfacesCons = ExampleCons::new;
		interfacesCons.display("Method reference to a constructor - ExampleCons::new");

		System.out.println("Method reference to an instance method of an arbitrary object of a particular type - list.sort(String::compareToIgnoreCase)");
		List<String> list = Arrays.asList("C", "B", "A", "E", "D", "F");
		list.sort(String::compareToIgnoreCase);
		System.out.println(list);
		
		System.out.println("---------------------------------------------------------------------------");
	}

	public interface Interfaces
	{
		public void display(Object obj);
		default void display()
		{
			System.out.println("Vinayak-Mahadev");
		}
	}

	public interface InterfacesCons
	{
		public ExampleCons display(Object obj);

	}

	public class Example
	{
		public void display(Object obj) {
			System.out.println(obj);
		}

	}

	public class ExampleCons
	{
		public ExampleCons(Object obj) 
		{
			System.out.println(obj);
		}
	}
}

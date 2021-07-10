package com.java8.practices.basics.lamda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class MapReduceConcepts 
{
	public static void main(String[] args) 
	{
		List<Employee> list = getEmpRepository();
		System.out.println(list);
		System.out.println("Avg age : " + list.stream().filter(e -> e != null).mapToInt(e -> e.getAge()).average());
		
		
	}

	public static List<Employee> getEmpRepository()
	{
		List<Employee> list = new ArrayList<Employee>();

		IntStream.range(1, 10).forEach(i -> list.add(new Employee(i, "Emp-"+i, i+10)));
		Collections.shuffle(list);
		return list;
	}


	public static class Employee
	{ 
		private final int id;

		private final String name;

		private final int age;

		public Employee(int id, String name, int age)
		{
			this.id = id;
			this.name = name;
			this.age = age; 
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}

		@Override
		public String toString() 
		{
			return  id + "-" + name + "-" + age;
		}


	}
}

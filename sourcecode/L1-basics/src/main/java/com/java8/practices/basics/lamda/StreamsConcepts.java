package com.java8.practices.basics.lamda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StreamsConcepts 
{

	public void runSample() 
	{
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("StreamsConcepts : Streams and Lambda Example");
		System.out.println("---------------------------------------------------------------------------");
		List<Integer> numberList = Arrays.asList(10, 13, 40, 11, 56, 100, 33, 25, 67, 86, 98, 1, -1, -9, 76);

		System.out.println("print all number list :: ");
		//
		System.out.println("Approch 1");
		numberList.forEach(System.out::print);

		System.out.println("\nApproch 2");
		numberList.forEach(num->System.out.print(num + ","));

		System.out.println("\nApproch 3");
		
		numberList.forEach((num)->
		{
			System.out.print(num + ",");
		}
				);

		List<Student> list = studentRepo();
		System.out.println(list);

		System.out.println("---------------------------------------------------------------------------");
	}


	public static class Student
	{
		private Long id;
		private String name;
		private String sclOrClg;

		public Student(Long id, String name, String sclOrClg) 
		{
			super();
			this.id = id;
			this.name = name;
			this.sclOrClg = sclOrClg;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSclOrClg() {
			return sclOrClg;
		}
		public void setSclOrClg(String sclOrClg) {
			this.sclOrClg = sclOrClg;
		}

		@Override
		public String toString() {
			return "Student [id=" + id + ", name=" + name + ", S/C=" + sclOrClg + "]";
		}

	}


	public List<Student> studentRepo()
	{
		List<Student> list = new ArrayList<Student>();
		for (long i = 1; i < 100; i++) 
			if(i/2==0)
				list.add(new Student(i, "S"+i, "Scl"));
			else
				list.add(new Student(i, "S"+i, "Clg"));

		Collections.shuffle(list);
		return list;
	}
}

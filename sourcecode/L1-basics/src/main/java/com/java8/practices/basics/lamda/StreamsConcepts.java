package com.java8.practices.basics.lamda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamsConcepts 
{

	/**
	 * <pre>
	 * 1. forEach
	 * 2. filter
	 * </pre>
	 */
	public void runSample() 
	{
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("StreamsConcepts : Streams, Lambda and Collectors Example");
		System.out.println("---------------------------------------------------------------------------");
		final List<Integer> numberList = Arrays.asList(10, 13, 40, 11, 56, 100, 33, 25, 67, 86, 98, 1, -1, -9, 76);
		
		// joiner
		StringJoiner joiner = new StringJoiner(",");
		
		numberList.stream().map(n -> n.toString()).forEach(joiner::add);
		System.out.println("String joiner :: " + joiner.toString());
		
		System.out.println("\nPrint all number list");
		//
		System.out.println("Number list :: Approch 1");
//		numberList.forEach(joiner::add);

		System.out.println("\n\nNumber list :: Approch 2");
		numberList.forEach(num->System.out.print(num + ","));

		System.out.println("\n\nNumber list :: Approch 3");

		numberList.forEach((num)->
		{
			System.out.print(num + ",");
		});

		System.out.print("\n\nSquare Number list :: ");
		System.out.println(numberList.stream().map(n -> n*n).collect(Collectors.toList()));

		System.out.print("\nPrint the value is more than 50 in Number list :: ");
		numberList.stream().filter(num-> (num > 50)).forEach(num->System.out.print(num + ","));

		System.out.print("\n\nCollect the list the value is more than 50 in Number list :: ");
		List<Integer> temp = numberList.stream().filter(num-> (num > 50)).collect(Collectors.toList());
		System.out.println(temp + "\n");
		

		System.out.print("Sort the Number list :: ");
		numberList.sort((c1,c2) -> {
			if(c1<c2) return -1; return 1; 
		}); 
		System.out.println(numberList + "\n");

		List<Student> studentList = studentRepo();

		System.out.print("Print all student list :: ");
		studentList.forEach(System.out::print);

		System.out.print("\n\nPrint all student name :: ");
		studentList.forEach(s -> System.out.print(s.getName()+","));

		// filter , map , collect example
		System.out.print("\n\nCollect the student name list from student list :: ");
		List<String> nameList = studentList.stream().filter(s -> s.getName() != null).map(s-> s.getName()).collect(Collectors.toList());
		System.out.println(nameList + "\n");

		
		System.out.print("Get Scl's student objects as list :: ");
		List<Student> sclList = studentList.stream().filter(s -> s.getSclOrClg().equals("Scl")).collect(Collectors.toList());
		System.out.println(sclList + "\n");
		
		System.out.print("Collect as Map (key - student id, value - student name) :: ");
		Map<Long,String> map = studentList.stream().collect(Collectors.toMap(Student::getId, Student::getName));
		System.out.println(map+"\n");
		
		System.out.print("Collect Scl and Clg student count as map :: ");
		Map<String,Long> countMap = studentList.stream().collect(Collectors.groupingByConcurrent(Student::getSclOrClg, Collectors.counting()));
		System.out.println(countMap+"\n"); // groupingBy or groupingByConcurrent
		
		System.out.println("String[] into Map");
		String[] arr = {"vinay","a","b","vinay","c"};
		System.out.println(Arrays.toString(arr));
		Arrays.stream(arr).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).forEach((k,v)->System.out.println(k +" : " + v));;

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
		for (long i = 1; i < 5; i++) 
			if(i/2==0)
				list.add(new Student(i, "S"+i, "Scl"));
			else
				list.add(new Student(i, "S"+i, "Clg"));

		Collections.shuffle(list);
		return list;
	}
}

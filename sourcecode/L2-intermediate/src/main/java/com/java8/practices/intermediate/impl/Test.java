package com.java8.practices.intermediate.impl;

public class Test 
{

	public static void main(String[] args) 
	{
		
		LinkedList<Integer> list = new LinkedList<Integer>();
		
		list.insert(100);
		list.insert(101);
		list.insert(102);
		list.insert(103);
		list.insert(104);
		list.insert(105);
		list.insert(106);
		list.insert(107);
		list.insert(108);
		list.insert(109);
		list.insert(110);
		list.insert(111);
		list.insert(112);
		list.insert(113);
		list.insert(114);
		list.insert(115);
		list.insert(116);
		list.insert(117);
		list.insert(118);
		list.insert(119);
		list.insert(120);
		
		System.out.println(list.getSize());
		list.print();
		
		list.remove(1);
		list.print();
		System.out.println(list.getSize());
		System.out.println(list.get(5));
		
		System.out.println(list.getFirst());
		System.out.println(list.getLast());
		System.out.println("0 : " + list.getNode(0));
		System.out.println("10 : " + list.getNode(10));
		System.out.println("21 : " + list.getNode(20));
//		list.removeAll();
	}

}

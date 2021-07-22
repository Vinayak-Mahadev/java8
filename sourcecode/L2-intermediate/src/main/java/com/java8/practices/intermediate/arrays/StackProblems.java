package com.java8.practices.intermediate.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class StackProblems 
{

	public static void main(String[] args) 
	{
		String arr[] = {"{[()]}", "{[()]", "{(][)}", "{}", null, "[]"};

		System.out.println(Arrays.toString(arr) + "\n");
		for (String s : arr) 
			System.out.println(s + " :: " + numberOfCurlyBracesPair(s));
		System.out.println(findMaxBinaryGapFromInteger(101));
		System.out.println(findMaxBinaryGapFromInteger(9));
		System.out.println(findMaxBinaryGapFromInteger(200));
	}

	public static int findMaxBinaryGapFromInteger(int num)
	{
		System.out.println("---------------------------------------------------------");
		int result = 0;
		Stack<Character> stack = null;
		Character item = null;
		Character pop = null;
		String binaryNum = null; 
		List<Integer> list = new ArrayList<Integer>();
		try 
		{
			if(num == 0)
				return result;
			stack = new Stack<Character>();
			binaryNum = Integer.toBinaryString(num);
			System.out.println("BinaryString :: " + binaryNum);
			int zeroCount = 0;
			for (int i = 0; i < binaryNum.length(); i++) 
			{
				item = binaryNum.charAt(i);
				if(item == '1')
				{
					if(!stack.isEmpty())
					{
						pop = stack.pop();
						if(pop == '1')
							zeroCount = 0;							
					}
					stack.push(item);
				}
				else
				{
					if(!stack.isEmpty())
					{
						pop = stack.pop();
						if(pop == '1')
						{
							list.add(zeroCount);
							zeroCount = 0;							
						}
					}
					zeroCount++;
				}
			}
			if(!stack.isEmpty())
			{
				pop = stack.pop();
				if(item == '1' && pop == '1')
					list.add(zeroCount);
			}
			System.out.println(list);
			Collections.sort(list);
			result =  list.get(list.size()-1);
		}
		catch (Exception e) 
		{

		}
		finally
		{
			item = null;
			pop = null;
			stack = null;
			binaryNum = null;;
		}
		System.out.println("---------------------------------------------------------");
		return result;
	}

	public static boolean numberOfCurlyBracesPair(String str)
	{
		Stack<Character> stack = null;
		Character item = null;
		Character pop = null;
		int n = 0;
		try 
		{
			if(str == null || str.trim().isEmpty() || str.length()%2 != 0)
				return false;
			n = str.length();
			stack = new Stack<Character>();
			for (int i = 0; i < n; i++) 
			{
				item = str.charAt(i);
				if(item == '{' || item == '[' || item == '(')
					stack.push(item);
				else
				{
					if(stack.isEmpty())
						return false;
					else
					{
						pop = stack.pop();
						if(item == '{' && pop != '}') return false;
						else if(item == '[' && pop != ']') return false;
						else if(item == '(' && pop != ')') return false;
					}
				}
			}
			return stack.isEmpty() ? true : false;
		}
		catch (Exception e) 
		{

		}
		finally 
		{
			stack = null;	
			item = null;
			pop = null;
		}
		return true;
	}
}

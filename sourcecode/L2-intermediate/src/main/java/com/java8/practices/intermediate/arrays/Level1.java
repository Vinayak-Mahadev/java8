package com.java8.practices.intermediate.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Level1 {

	public static void main(String[] args) 
	{
		// Check if a key is present in every segment of size k in an array
		int[] arr = { 21, 23, 56, 65, 34, 54, 76, 32, 23, 45, 21, 23, 25};
		int element = 23;
		int segment = 5;
		segmentExample(arr, element, segment);


		// Program to find the minimum (or maximum) element of an array
		int minMax[] = { 21, 23, 56, 65, 34, 54, 76, 32, 23, 45, 21, 23, 25};
		findMinAndMax(minMax);

		// Program to reverse an array or string
		int reverse[] = { 21, 23, 56, 65, 34, 54, 76, 32, 23, 45, 21, 23, 25};
		reverseAnArray(reverse);

		// Write a program to sort the given array
		//		int sortArr[] = { 91, 23, 56, 65, 34, 54, 76, 32, 23, 45, 21, 23, 25};
		int sortArr[] = {0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1};
		sortAnArray(sortArr);

		// Write a program to sort the given array
		int frequencyArr[] = { 91, 23, 56, 65, 34, 54, 76, 32, 23, 45, 21, 23, 25};
		frequencyOfNumber(frequencyArr, 23);

		// Find the frequency map of a number in an array
		int frequencyMapArr[] = { 91, 23, 56, 65, 34, 54, 76, 32, 23, 45, 21, 23, 25};
		frequencyOfNumberMap(frequencyMapArr);

		// Range and Coefficient of range of Array
		int coefficientOfRangeArr[] = { 91, 23, 56, 65, 34, 54, 76, 32, 23, 45, 21, 23, 25};
		coefficientOfRange(coefficientOfRangeArr);

		// Move all negative numbers to beginning and positive to end with constant extra space
		int neqArr[] = {-12, 11, -13, -5, 6, -7, 5, -3, -6};
		moveNegativeNumFirst(neqArr);
		
	}


	/**<pre>
	 * <b>Move all negative numbers to beginning and positive to end with constant extra space</b>
	 * result = (max-min)/(max+min)
	 * </pre>
	 */
	public static void moveNegativeNumFirst(int[] arr)
	{
		System.out.println("Move all negative numbers to beginning and positive to end with constant extra space");
		System.out.println("----------------------------------------------------------------");

		System.out.print("Input  :: ");
		Arrays.stream(arr).forEach(a -> System.out.print(a+" "));
		int j = 0 , temp = 0;
		for (int i = 0; i < arr.length; i++) 
		{
			if(arr[i] < 0)
			{
				if(i != j)
				{
					temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
				j++;
			}
		}

		System.out.print("\nOutput :: ");
		Arrays.stream(arr).forEach(a -> System.out.print(a+" "));
		System.out.println();
		System.out.println("----------------------------------------------------------------");
	}

	/**<pre>
	 * <b>Range and Coefficient of range of Array</b>
	 * result = (max-min)/(max+min)
	 * </pre>
	 */
	public static void coefficientOfRange(int[] arr)
	{
		System.out.println("Range and Coefficient of range of Array");
		System.out.println("----------------------------------------------------------------");

		int min = arr[0];
		int max = arr[0];
		float result = 0;
		System.out.print("Input  :: ");
		for (int i = 0; i < arr.length; i++) 
		{
			System.out.print(arr[i]+" ");
			min = Math.min(min, arr[i]);
			max = Math.max(max, arr[i]);
		}
		result = (float)(max-min)/(float)(max+min);
		System.out.println("\nmin:max :: " + min + ":" + max);
		System.out.println("Output :: " + result);
		System.out.println("----------------------------------------------------------------");
	}

	/**<pre>
	 * <b>Find the frequency map of a number in an array</b>
	 * </pre>
	 */
	public static void frequencyOfNumberMap(int[] arr)
	{
		System.out.println("Find the frequency map of a number in an array");
		System.out.println("----------------------------------------------------------------");

		System.out.print("Input  :: ");

		List<Integer> list = new ArrayList<Integer>();
		Arrays.stream(arr).forEach(a -> {System.out.print(a+" "); list.add(a);});
		Map<Integer, Long> x = list.stream().collect(Collectors.groupingByConcurrent(i -> Integer.parseInt(i+""), Collectors.counting()));

		System.out.print("\nOutput :: frequency map : " + x);
		System.out.println();
		System.out.println("----------------------------------------------------------------");
	}

	/**<pre>
	 * <b>Find the frequency of a number in an array</b>
	 * </pre>
	 */
	public static void frequencyOfNumber(int[] arr, int x)
	{
		System.out.println("Find the frequency of a number in an array");
		System.out.println("----------------------------------------------------------------");

		System.out.print("Input  :: ");

		long count = Arrays.stream(arr).filter(a ->
		{
			System.out.print(a+" ");
			if(a==x) return true;
			return false;
		}).count();


		System.out.print("\nOutput :: frequency : " + x + " :: " + count);
		System.out.println();
		System.out.println("----------------------------------------------------------------");
	}

	/**<pre>
	 * <b>Write a program to sort the given array</b>
	 * </pre>
	 */
	public static void sortAnArray(int[] arr)
	{
		System.out.println("Write a program to sort the given array");
		System.out.println("----------------------------------------------------------------");

		System.out.print("Input :: ");
		Arrays.stream(arr).forEach(a -> System.out.print(a+" "));
		for (int i = 0; i < arr.length; i++) 
			for (int j = i; j < arr.length; j++)
				if(arr[i] > arr[j])
				{
					int tmp = arr[i];
					arr[i] = arr[j];
					arr[j] = tmp;
				}

		System.out.print("\nOutput :: ");
		Arrays.stream(arr).forEach(a -> System.out.print(a+" "));
		System.out.println();
		System.out.println("----------------------------------------------------------------");
	}

	/**<pre>
	 * <b>Program to reverse an array or string</b>
	 * </pre>
	 */
	public static void reverseAnArray(int[] arr)
	{
		System.out.println("Program to reverse an array or string");
		System.out.println("----------------------------------------------------------------");
		int start = 0;
		int end = arr.length-1;

		System.out.print("Input :: ");
		Arrays.stream(arr).forEach(a -> System.out.print(a+" "));
		while (start < end) 
		{
			int temp = arr[start];
			arr[start] = arr[end];
			arr[end] = temp;
			start ++;
			end --;
		}

		System.out.print("\nOutput :: ");
		Arrays.stream(arr).forEach(a -> System.out.print(a+" "));
		System.out.println();
		System.out.println("----------------------------------------------------------------");
	}

	/**<pre>
	 * <b>Program to find the minimum (or maximum) element of an array</b>
	 * </pre>
	 */
	public static void findMinAndMax(int[] arr)
	{
		System.out.println("Program to find the minimum (or maximum) element of an array");
		System.out.println("----------------------------------------------------------------");

		int min = arr[0];
		int max = arr[0];
		for (int i = 0; i < arr.length; i++) 
		{
			max = Math.max(max, arr[i]);
			min = Math.min(min, arr[i]);
		}
		System.out.print("Input :: ");
		Arrays.stream(arr).forEach(a -> System.out.print(a+" "));

		System.out.println("\nOutput :: min : " + min + " :: max : " + max);

		System.out.println("----------------------------------------------------------------");
	}

	/**<pre>
	 * <b>Check if a key is present in every segment of size k in an array</b>
	 * 
	 * Given an array arr[] and size of array is n and one another key x, and give you a segment size k. The task is to find that the key x present in every segment of size k in arr[].
	 * Examples: 
	 * Input : 
	 * arr[] = { 3, 5, 2, 4, 9, 3, 1, 7, 3, 11, 12, 3} 
	 * x = 3 
	 * k = 3 
	 * Output : Yes 
	 * There are 4 non-overlapping segments of size k in the array, {3, 5, 2}, {4, 9, 3}, {1, 7, 3} and {11, 12, 3}. 3 is present all segments.
	 * Input : 
	 * arr[] = { 21, 23, 56, 65, 34, 54, 76, 32, 23, 45, 21, 23, 25} 
	 * x = 23 
	 * k = 5 
	 * Output :Yes 
	 * There are three segments and last segment is not full {21, 23, 56, 65, 34}, {54, 76, 32, 23, 45} and {21, 23, 25}. 
	 * 23 is present all window.
	 * Input :arr[] = { 5, 8, 7, 12, 14, 3, 9} 
	 * x = 8 
	 * k = 2 
	 * Output : No
	 * </pre>
	 */
	public static void segmentExample(int[] arr, int element, int segment)
	{
		// O(n)
		System.out.println("Check if a key is present in every segment of size k in an array");
		System.out.println("----------------------------------------------------------------");
		try 
		{
			System.out.print("Input :: ");
			Arrays.stream(arr).forEach(a -> System.out.print(a+" "));
			System.out.println("\nelement :: " + element + " :: segment :: " + segment);
			boolean flag = false;
			int check = 0;

			for (int i = 0; i < arr.length; i++) 
			{
				if(element == arr[i])
					flag = true;

				if(check++ >= segment-1)
				{
					check = 0;
					if(!flag) break;
					flag = false;
				}

			}
			System.out.println("Output :: " + flag);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		System.out.println("----------------------------------------------------------------");
	}


}

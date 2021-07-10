package com.java8.practices.basics.lamda;

import java.util.stream.IntStream;

public class SeqVsParallelStreams 
{
//	private static String TIME = " ms";
	private static String TIME = " nanoseconds";
	public static void main(String[] args) 
	{
		int a = 0;
		int b = 100000;
		int itr = 10;
		Long start = null;
		Long end = null;

		long forEachSequence = 0;
		long forEachParallel = 0;
		long forEachOrderedSequence = 0;
		long forEachOrderedParallel = 0;

		for (int i = 1; i <= itr; i++) 
		{
			start = getTime();
			IntStream.range(a,b).filter((c) -> c!=0).forEach(SeqVsParallelStreams::run);
			end = getTime();
			System.out.println(i + " : forEach sequence        :: " + (end-start) + TIME);
			forEachSequence +=(end-start);


			start = getTime();
			IntStream.range(a,b).filter((c) -> c!=0).parallel().forEach(SeqVsParallelStreams::run);
			end = getTime();
			System.out.println(i + " : forEach parallel        :: " + (end-start) + TIME);
			forEachParallel +=(end-start);


			start = getTime();
			IntStream.range(a,b).filter((c) -> c!=0).forEachOrdered(SeqVsParallelStreams::run);
			end = getTime();
			System.out.println(i + " : forEachOrdered sequence :: " + (end-start) + TIME);
			forEachOrderedSequence +=(end-start);


			start = getTime();
			IntStream.range(a,b).filter((c) -> c!=0).parallel().forEachOrdered(SeqVsParallelStreams::run);
			end = getTime();
			System.out.println(i + " : forEachOrdered parallel :: " + (end-start) + TIME);
			forEachOrderedParallel +=(end-start);


			System.out.println("-------------------------------------------");
		}

		System.out.println("Avg forEachSequence          :: " + (forEachSequence/itr)+ TIME);
		System.out.println("Avg forEachParallel          :: " + (forEachParallel/itr)+ TIME);
		System.out.println("Avg forEachOrderedSequence   :: " + (forEachOrderedSequence/itr)+ TIME);
		System.out.println("Avg forEachOrderedParallel   :: " + (forEachOrderedParallel/itr)+ TIME);
	}

	public static void run(Object o)
	{
		try 
		{
			//			Thread.sleep(1l);
		}
		catch (Exception e) 
		{
		}
	}

	public static long getTime()
	{
		if(TIME.trim().equals("nanoseconds"))
			return System.nanoTime();
		return System.currentTimeMillis();
	}
}

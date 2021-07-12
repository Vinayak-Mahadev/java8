package com.java8.practices.intermediate.threads;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class ExecutorFrameWork 
{
	public static void newSingleThreadExecutor() 
	{
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("ExecutorFrameWork : newSingleThreadExecutor Example");
		System.out.println("---------------------------------------------------------------------------");

		ExecutorService executor = null;
		try 
		{
			executor = Executors.newSingleThreadExecutor();
			executor.execute(ExecutorFrameWork::test);
			executor.shutdown();
		}
		catch (Exception e) 
		{

		}
	}

	public static void newFixedThreadPool() 
	{
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("ExecutorFrameWork : newFixedThreadPool Example");
		System.out.println("---------------------------------------------------------------------------");
		ExecutorService executor = null;
		try 
		{
			executor = Executors.newFixedThreadPool(5);
			for (int j = 1; j <= 15; j++) 
				executor.execute(ExecutorFrameWork::test);

			executor.shutdown();
		}
		catch (Exception e) 
		{

		}

	}

	/*
	public static void callable()
	{
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("ExecutorFrameWork : Callable Example");
		System.out.println("---------------------------------------------------------------------------");

		ExecutorService executorService = null;
		List<Future<Integer>> list = null;
		Set<Callable<Integer>> callables = new HashSet<Callable<Integer>>();
		StringJoiner joiner = new StringJoiner(",", "[", "]");
		try 
		{
			executorService = Executors.newFixedThreadPool(5);
			IntStream.range(1, 50).forEach(i -> callables.add(ExecutorFrameWork::test));

			list = executorService.invokeAll(callables);
			list.forEach(f -> {
				try 
				{
					if(f.isDone())
						joiner.add(f.get().toString());
					else
						System.out.println(f.isCancelled());

				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}});

			executorService.shutdown();
			System.out.println("Output :: " + joiner);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
	}
	 */

	public static int test()
	{
		System.out.println("Thread id : name :: <" + Thread.currentThread().getId() + "> : " + Thread.currentThread().getName());
		synchronized (ExecutorFrameWork.RES) {
			RES ++;
		}
		return RES;
	}

	public static Integer RES = 0;
}

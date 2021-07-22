package com.java8.practices.intermediate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App 
{
	public final static int THREAD_POOL_SIZE = 5;

	public static Map<String, Integer> HashMapObject = null;
	public static Map<String, Integer> HashTableObject = null;
	public static Map<String, Integer> SynchronizedMapObject = null;
	public static Map<String, Integer> ConcurrentHashMapObject = null;


	public static void main(String[] args) throws Exception 
	{

		/*
		 ThreadsConcepts.sample();
		ExecutorFrameWork.newSingleThreadExecutor();
		ExecutorFrameWork.newFixedThreadPool();
		 */

		// Test with Hashtable Object
		HashMapObject = new HashMap<String, Integer>();
		mapPerformTest(HashMapObject);

		
		// Test with ConcurrentHashMap Object
		ConcurrentHashMapObject = new ConcurrentHashMap<String, Integer>();
		mapPerformTest(ConcurrentHashMapObject);

		// Test with Hashtable Object
		HashTableObject = new Hashtable<String, Integer>();
		mapPerformTest(HashTableObject);

		// Test with synchronizedMap Object
		SynchronizedMapObject = Collections.synchronizedMap(new HashMap<String, Integer>());
		mapPerformTest(SynchronizedMapObject);

	}


	public static void mapPerformTest(final Map<String, Integer> threads) throws InterruptedException 
	{

		System.out.println("Test started for: " + threads.getClass());
		long averageTime = 0;
		for (int i = 0; i < 5; i++) {

			long startTime = System.nanoTime();
			ExecutorService crunchifyExServer = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

			for (int j = 0; j < THREAD_POOL_SIZE; j++) {
				crunchifyExServer.execute(
						new Runnable() {
							@SuppressWarnings("unused")
							@Override
							public void run() 
							{

								for (int i = 0; i < 500000; i++) 
								{
									Integer crunchifyRandomNumber = (int) Math.ceil(Math.random() * 550000);

									// Retrieve value. We are not using it anywhere
									Integer crunchifyValue = threads.get(String.valueOf(crunchifyRandomNumber));

									// Put value
									threads.put(String.valueOf(crunchifyRandomNumber), crunchifyRandomNumber);
								}
							}
						});
			}

			// Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted. Invocation
			// has no additional effect if already shut down.
			// This method does not wait for previously submitted tasks to complete execution. Use awaitTermination to do that.
			crunchifyExServer.shutdown();

			// Blocks until all tasks have completed execution after a shutdown request, or the timeout occurs, or the current thread is
			// interrupted, whichever happens first.
			crunchifyExServer.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

			long entTime = System.nanoTime();
			long totalTime = (entTime - startTime) / 1000000L;
			averageTime += totalTime;
			System.out.println("500K entried added/retrieved in " + totalTime + " ms");
		}
		System.out.println("For " + threads.getClass() + " the average time is " + averageTime / 5 + " ms\n\n");
	}
}

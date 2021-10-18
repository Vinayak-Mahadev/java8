package com.java8.practices.intermediate.threads.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * <pre>
 * <b>Concurrency Pattern</b>
 * --------------------------
 *  
 *1.  Processor -> Broker -> Processor 
 * 
 *2.  Producer -> Broker -> Consumer
 *
 *3.  Producer -> Broker -> List of Consumer
 * </pre>
 * 
 * @author Vinayak-Mahadev
 *
 */
public class App 
{

	public static void main(String[] args) throws Exception
	{

		//		oneToMany(Broker.builder().queueSize(10).poll(30).build(), 10);
		oneToManyTradtionalWay(Broker.builder().queueSize(10).poll(30).build(), 10);
	}


	public static void oneToMany(Broker broker, int consumers) throws InterruptedException, ExecutionException
	{
		long start = System.currentTimeMillis();
		ExecutorService threadPool = Executors.newFixedThreadPool(consumers+1);

		for (int i = 0; i < consumers; i++) 
		{
			threadPool.execute(new Consumer(broker));
		}
		//		IntStream.range(0, consumers).forEach(i -> threadPool.execute(new Consumer(broker)));
		Future<?> producerStatus = threadPool.submit(new Producer(broker));
		System.out.println("ProducerStatus :: " + producerStatus.get());
		threadPool.shutdown();
		long end = System.currentTimeMillis();
		System.out.println("oneToMany :: " + (end-start));
	}



	public static void oneToManyTradtionalWay(Broker broker, int consumers) throws InterruptedException, ExecutionException
	{
		long start = System.currentTimeMillis();
		String a = new String();
		
		synchronized(a)
		{

			List<Thread> processors = new ArrayList<Thread>(consumers+1);

			for (int i = 0; i < consumers; i++) 
			{
				Thread t = new Thread(new Consumer(broker));
				t.setName("consumer - "+i);
				processors.add(t);
			}

			Thread t = new Thread(new Producer(broker));
			t.setName("producer - "+1);


			processors.add(t);

			for (Thread thread : processors) 
			{
				thread.start();
			}
		}
		
		long end = System.currentTimeMillis();

		System.out.println("oneToManyTradtionalWay :: " + (end-start));
	}
}

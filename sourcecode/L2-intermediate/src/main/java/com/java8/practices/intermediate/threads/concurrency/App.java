package com.java8.practices.intermediate.threads.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
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
		long start = System.currentTimeMillis();

		oneToMany(new Broker(1, 10, 200), 1);
		
		long end = System.currentTimeMillis();

		System.out.println("oneToMany :: " + (end-start));
	}


	public static void oneToMany(Broker broker, int consumers) throws InterruptedException, ExecutionException
	{
		ExecutorService threadPool = Executors.newFixedThreadPool(consumers+1);
		IntStream.range(0, consumers).forEach(i -> threadPool.execute(new Consumer(broker)));
		Future<?> producerStatus = threadPool.submit(new Producer(broker));
		System.out.println("ProducerStatus :: " + producerStatus.get());
		threadPool.shutdown();
	}
}

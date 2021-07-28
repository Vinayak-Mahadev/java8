package com.java8.practices.intermediate.threads.concurrency;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Broker 
{
	protected volatile ArrayBlockingQueue<Message> queue;
	protected volatile ArrayList<Exception> noData;

	protected int poll;

	protected int offer;

	public Broker()
	{
		this(100,100,100);
	}

	public Broker(int capacity, int poll, int offer)
	{
		queue = new ArrayBlockingQueue<Message>(capacity);	
		noData = new ArrayList<>();	
		this.poll = poll;
		this.offer = offer;
	}

	public void put(Message data) throws InterruptedException
	{
		if(data == null) return;
		//        this.queue.put(data);
		this.queue.offer(data, offer, TimeUnit.MILLISECONDS);
	}

	public Message get() throws InterruptedException
	{
		return this.queue.poll(poll, TimeUnit.MILLISECONDS);
	}
}

package com.java8.practices.intermediate.threads.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Broker 
{
	protected volatile BlockingQueue<Message> queue;
	protected volatile List<Exception> noData;

	@Builder.Default
	protected int queueSize = 10;

	@Builder.Default
	protected int poll = 10;

	@Builder.Default
	protected int offer = 0;

	public void put(Message data) throws InterruptedException
	{
		if(queue == null) queue = new ArrayBlockingQueue<Message>(queueSize);
		if(noData == null) noData = new ArrayList<>(1);
		if(data == null) return;

		if(offer == 0)
			this.queue.put(data);
		else
			this.queue.offer(data, offer, TimeUnit.MILLISECONDS);
	}

	public Message get() throws InterruptedException
	{
		if(queue == null) queue = new ArrayBlockingQueue<Message>(queueSize);
		if(noData == null) noData = new ArrayList<>(1);
		return this.queue.poll(poll, TimeUnit.MILLISECONDS);
	}

	public boolean isQueueFull() 
	{
		if(queue == null) return false;
		return queue.size() == queueSize ? true : false;
	}

	public boolean isQueueEmpty() 
	{
		if(queue != null)
			return queue.isEmpty();
		return true;
	}
}

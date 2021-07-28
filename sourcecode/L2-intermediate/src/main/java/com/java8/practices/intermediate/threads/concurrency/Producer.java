package com.java8.practices.intermediate.threads.concurrency;

public class Producer extends Processor
{
	private final long totalCount = 100;
	private long checkCount = 1;

	Producer(Broker broker)
	{
		super(broker, false, true);
	}

	@Override
	public void init() 
	{
		System.out.println("Producer :: "+getProcessId()+" inited");
	}

	@Override
	public Message produce() 
	{
		if(checkCount > totalCount) return new Message(false);
		try 
		{
			Thread.sleep(1);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		return new Message(checkCount++, "Vinayak-Mahadev");
	}

	@Override
	public void consume(Message message) 
	{

	}

	@Override
	protected void stop() 
	{
		System.out.println("Producer :: "+getProcessId()+" stopped");
	}
}

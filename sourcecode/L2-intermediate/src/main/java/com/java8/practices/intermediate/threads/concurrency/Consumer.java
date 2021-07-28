package com.java8.practices.intermediate.threads.concurrency;

public class Consumer extends Processor
{
	public Consumer(Broker broker)
	{
		super(broker, true, false);
	}
	
	@Override
	public void init() 
	{
		System.out.println("Consumer :: "+getProcessId()+" inited");
	}

	@Override
	public Message produce() { return null; }

	@Override
	public void consume(Message message) 
	{
		System.out.println("Consumer :: " + getProcessId() + " : "+ message.getId() + " " + message.getMessage());
	}

	@Override
	protected void stop() 
	{
		System.out.println("Consumer :: "+getProcessId()+" stopped");
	}
}

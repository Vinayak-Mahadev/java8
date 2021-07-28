package com.java8.practices.intermediate.threads.concurrency;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Processor implements Runnable
{
	protected boolean consumer;

	protected boolean producer;

	protected Broker broker;

	protected final String processId = getRequestId().toString();

	public Processor()
	{
		init();
	}

	public Processor(Broker broker, boolean consumer, boolean producer)
	{
		this();
		this.broker = broker;	
		this.consumer = consumer;	
		this.producer = producer;	
	}

	public abstract void init();

	public abstract Message produce();

	public abstract void consume(Message message);

	protected abstract void stop();

	@Override
	public final void run() 
	{
		while (true) 
		{
			Message message = null;
			try 
			{
				if(consumer && producer)
				{
					stop();
					break;
				}
				else if (producer)
				{
					message = produce();

					if(message != null && message.isDataAvailable())
					{
						broker.put(message);
					}
					else if(message != null && !message.isDataAvailable())
					{
						broker.put(message);
						broker.noData.add(new Exception("No Data"));
						stop();
						break;
					}
				}
				else if(consumer)
				{
					message = broker.get();

					if(message != null && message.isDataAvailable()) 
					{
						consume(message);
					}
					else if((message != null && !message.isDataAvailable()) || !broker.noData.isEmpty()) 
					{
						stop();
						break;
					}
				}
				else
					break;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally 
			{
				message = null;
			}
		}
	}

	public void setBroker(Broker broker)
	{
		this.broker = broker;
	}

	public String getProcessId()
	{
		return processId;
	}

	private static int COUNT = 6000;
	private static final int MAX_COUNT = 6999;
	private static final int SLEEP_TIME_FOR_NEXT_ITERATION = 1000;

	private static synchronized Long getRequestId()
	{
		COUNT++;

		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyMMddhhmmssSSS");

		if (COUNT > MAX_COUNT)
		{
			try
			{
				Thread.sleep(SLEEP_TIME_FOR_NEXT_ITERATION);
			}
			catch (InterruptedException interruptedException)
			{

			}
			finally
			{
				COUNT = 6001;
			}
		}

		return Long.parseLong(dateFormatter.format(new Date()) + String.format("%04d", COUNT));
	}
}

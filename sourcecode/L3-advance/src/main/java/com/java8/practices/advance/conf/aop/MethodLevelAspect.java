package com.java8.practices.advance.conf.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Configuration
@Slf4j
public class MethodLevelAspect 
{
	private final String TrackTime = "@annotation(com.java8.practices.advance.conf.aop.TrackTime)";
	
	/**
	 * <pre>
	 * <b>Purpose</b>
	 * --------------
	 * Calculating method level execution time with {@link TrackTime} 
	 * </pre>
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Throwable
	 */
	@Around(TrackTime)
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable
	{
		Long startTime = System.currentTimeMillis();
		Long timeTaken = null;
		Object proceed = null;
		try 
		{
			proceed =  joinPoint.proceed();
			return proceed;
		}
		catch (Throwable e) 
		{
			throw e;
		}
		finally 
		{
			timeTaken = System.currentTimeMillis() - startTime.longValue();
			log.info("Time Taken by {} is {}", joinPoint, timeTaken+" ms");
			startTime = null;
			timeTaken = null;
			proceed = null;
		}
	}

}

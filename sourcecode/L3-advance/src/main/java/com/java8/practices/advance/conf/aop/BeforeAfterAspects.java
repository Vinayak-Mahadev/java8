package com.java8.practices.advance.conf.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import com.java8.practices.advance.common.Utility;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Configuration
@Slf4j
public class BeforeAfterAspects 
{
	private final String before = "execution(* com.java8.practices.advance.rest.*.*(..))"; 
	private final String after = "execution(* com.java8.practices.advance.rest.*.*(..))"; 
	private final String afterThrowing = "execution(* com.java8.practices.advance.rest.*.*(..))"; 
	private final String afterReturning = "execution(* com.java8.practices.advance.rest.*.*(..))"; 

	@Before(value = before)
	public void before(JoinPoint joinPoint)
	{
		Arrays.stream(joinPoint.getArgs()).filter(o -> o!=null).forEachOrdered((o)-> log.trace(o.getClass() + " : " + o.toString()));
		log.debug("@Before : Allowed execution for {} :: REQ_ID is {}", joinPoint.getSignature().getName(), Utility.getId());
	}

	@After(value = after)
	public void after(JoinPoint joinPoint)
	{
		Arrays.stream(joinPoint.getArgs()).filter(o -> o!=null).forEachOrdered((o)-> log.trace(o.getClass() + " : " + o.toString()));
		log.debug("@After : Allowed execution for {} :: REQ_ID is {}", joinPoint.getSignature().getName(), Utility.getId());
	}

	@AfterThrowing( pointcut = afterThrowing, throwing = "throwing")
	public void afterThrowing(JoinPoint joinPoint, @Nullable Object throwing)
	{
		if(throwing != null)
			log.trace("@AfterThrowing returnValue : " + throwing.getClass() + " : " + throwing);
		Arrays.stream(joinPoint.getArgs()).filter(o -> o!=null).forEachOrdered((o)-> log.trace("@AfterThrowing : " + o.getClass() + " : " + o.toString()));
		log.debug("@AfterThrowing : Allowed execution for {} :: REQ_ID is {}", joinPoint.getSignature().getName(), Utility.getId());
	}

	@AfterReturning(pointcut = afterReturning, returning = "returnValue")
	public void afterReturning(JoinPoint joinPoint, @Nullable Object returnValue)
	{
		if(returnValue != null)
			log.trace("@AfterReturning returnValue : " + returnValue.getClass() + " : " + returnValue);
		Arrays.stream(joinPoint.getArgs()).filter(o -> o!=null).forEachOrdered((o)-> log.trace("@AfterReturning : " +o.getClass() + " : " + o.toString()));
		log.debug("@AfterReturning : Allowed execution for {} :: REQ_ID is {}", joinPoint.getSignature().getName(), Utility.getId());
	}
}

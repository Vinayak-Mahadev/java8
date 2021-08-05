package com.java8.practices.advance.conf;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AopJoinPointConfig 
{
	private final String methodExecutionCalculationAspect = "execution(* com.java8.practices.advance.rest.*.*(..))";
	
	@Pointcut(methodExecutionCalculationAspect)
	public void methodExecutionCalculationAspect() {}
}

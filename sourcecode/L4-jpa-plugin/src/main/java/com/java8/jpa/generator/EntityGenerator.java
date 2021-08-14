package com.java8.jpa.generator;

import java.util.Map;

public interface EntityGenerator 
{
	public boolean generate(String input, String base) throws EntityGeneratorException;

	public boolean generate(Map<String,String> fileNameAndLocation) throws EntityGeneratorException;

	public static class EntityGeneratorException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;

		public EntityGeneratorException() 
		{
			this(null, null);
		}
		public EntityGeneratorException(String m) 
		{
			this(m, null);
		}
		public EntityGeneratorException(String m, Throwable t) 
		{
			
		}
	}
	
	public interface TriFunction<A,B,C,R> 
	{
		R apply(A a, B b, C c);
	}
}

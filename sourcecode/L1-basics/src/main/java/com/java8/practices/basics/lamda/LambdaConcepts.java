package com.java8.practices.basics.lamda;

/**
 * <pre>
 * () -> ()
 * () -> {}
 * (a,b) -> {}
 * (a,b) -> return a+b;
 * (a,b) -> {return a+b;}
 * str -> System.out.println(str)
 * System.out::println
 * </pre>
 * @author Vinayak
 *
 */
public class LambdaConcepts 
{
	public void runSamples() throws Exception
	{
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("LambdaConcepts : Functional Interface, Method reference and Lambda Example");
		System.out.println("---------------------------------------------------------------------------");
		int a = 10;
		int b = 0;
		Addition addition = null;
		AddObj addObj = new AddObj();
		// You can't invoke run method with reference, By class name only u have to invoke run method
		System.out.println("interface static method :: " + Addition.run(a, b)); 

		// traditional approach
		addition = new AdditionImpl();
		System.out.println("traditional approach :: " + addition.add(a, b));

		// u can call default method also
		addition.runWithDefault(a, b);
		
		// java8 approach 1
		addition = Add::perform;
		System.out.println("static method assign to interface approach :: " + addition.add(a, b));

		// java8 approach 2
		addition = addObj::perform;
		System.out.println("Object method assign to interface approach :: " + addition.add(a, b));
		
		// java8 approach 3
		addition = (i,j) ->
		{
			return i+j;
		};
		System.out.println("java8 approach 1 :: " + addition.add(a, b));

		// java8 approach 4
		addition = (i,j) -> (i+j);
		System.out.println("java8 approach 2 :: " + addition.add(a, b));

		// java8 approach 5
		addition = (i,j) -> i+j;
		System.out.println("java8 approach 3 :: " + addition.add(a, b));

		// java8 approach 6
		addition = (i,j) ->{
			int x = i;  // You can't declare duplicate key variable in same class
			int y = j;
			if(i == 0 || j == 0)
				throw new Exception("Invalid Inputs :: "+ i + "," + j);
			return x/y; 
		};

		try 
		{
			System.out.println("java8 approach 4 :: " + addition.add(20, 1));
			System.out.println("java8 approach 4 :: " + addition.add(a, b));
		}
		catch (Exception e) 
		{
			System.err.println(e);
		}

		System.out.println("---------------------------------------------------------------------------");
	}

	/**
	 * <pre>
	 * 1. No need to mention the annotation @FunctionalInterface at class level
	 * 2. If @FunctionalInterface means the interface should 
	 *    contains only one unimplemented method
	 * </pre>
	 */
	public static interface Addition
	{
		public int add(int a, int b) throws Exception;

		/* interface can have static implementation */
		public static int run(int a, int b) throws Exception
		{
			return a +b;
		} 
		
		default int runWithDefault(int a, int b) throws Exception
		{
			return a +b;
		}
	}

	public static class AdditionImpl implements Addition
	{
		@Override
		public int add(int a, int b) 
		{
			return a + b;
		}

		@Override
		public int runWithDefault(int a, int b) throws Exception {
			return Addition.super.runWithDefault(a, b);
		}
	}

	public static class Add
	{
		public static int perform(int a, int b)
		{
			return a + b;
		}
	}
	public static class AddObj
	{
		public int perform(int a, int b)
		{
			return a + b;
		}
	}
}

package com.java8.practices.basics;

import com.java8.practices.basics.lamda.LambdaConcepts;
import com.java8.practices.basics.lamda.StreamsConcepts;

/**
* <pre>
* <b>Purpose:</b><br>
*  Test the basic concepts for java 8 features. <br>
* <b>DesignReference:</b>
*
* <b>CopyRights:</b><br>
*  Enhancesys Innovations 2021<br>
* 
* <b>RevisionHistory:</b>
* <b>
* Sl No   Modified Date        Author</b>
* ==============================================
* 1        09-07-2021		   Vinayak Mahadev
* -- Base Release	
* </pre>
* 
* <br>
*/
public class App 
{
	public static LambdaConcepts lambdaConcepts = new LambdaConcepts();
	public static StreamsConcepts streamsConcepts = new StreamsConcepts();
	
	/**
	 * <pre>
	 * 1. Functional Interface
	 * 2. Lambda Expression
	 * 3. Streams [forEach, Filter, sorting in list & map]
	 * 4. Map & FlatMap
	 * 5. Optional
	 * 6. Map & Reduce mechanism
	 * 7. Parallel stream processing
	 * </pre> 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception 
	{
		System.out.println("Vinayak-Mahadev");
		
		/* LAMBDA EXAMPLES */
		// Example 1
		lambdaConcepts.functionalAndLambdaExample();
		// Example 2
		streamsConcepts.runSample();
	}

}

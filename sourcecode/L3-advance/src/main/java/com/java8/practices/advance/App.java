package com.java8.practices.advance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.java8.practices.advance.common.Props;
import com.java8.practices.advance.common.Utility;

@SpringBootApplication
public class App 
{
	private static Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static volatile ConfigurableApplicationContext _ApplicationContext_;

	public static void main(String[] args) 
	{
		Utility.setThreadId(1001l);
		SpringApplication application = null;
		try 
		{

			if(args != null && args.length >0 )
				Props.setProjectLoc(args[0].trim());

			application = new SpringApplication(App.class);
			//			application.setWebApplicationType(WebApplicationType.NONE);

			_ApplicationContext_  = application.run(args);
			LOGGER.info("Application started...");
			LOGGER.info("Props ProjectLoc :: " + Props.getProjectLoc());				

			System.out.println("AppRepository : " + _ApplicationContext_.getBean("appRepository"));
		} 
		catch (Exception e) 
		{
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		finally
		{
			application = null;
			LOGGER.info("Ended..");
		}
	}


}

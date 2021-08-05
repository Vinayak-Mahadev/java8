package com.java8.practices.advance;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ResourceUtils;

import com.java8.practices.advance.common.Props;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class App 
{
	public static volatile ConfigurableApplicationContext _ApplicationContext_;

	public static void main(String[] args) 
	{
		Thread.currentThread().setName("VinayaK");
		SpringApplication application = null;
		String content = null;
		String filePath = "spring.app.start.notification";
		try 
		{

			if(args != null && args.length >0 )
				Props.setProjectLoc(args[0].trim());

			application = new SpringApplication(App.class);
			//			application.setWebApplicationType(WebApplicationType.NONE);
			_ApplicationContext_  = application.run(args);
			log.info("Props ProjectLoc :: " + Props.getProjectLoc());				

			System.out.println("AppRepository : " + _ApplicationContext_.getBean("appRepository"));
			content = Files
					.lines(Paths.get(
							ResourceUtils.getFile(
									_ApplicationContext_
									.getEnvironment()
									.getProperty(filePath))
							.toURI()))
					.collect(Collectors.joining(System.lineSeparator()));
			log.info(content);
		} 
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		finally
		{
			application = null;
			content  = null;
		}
	}


}

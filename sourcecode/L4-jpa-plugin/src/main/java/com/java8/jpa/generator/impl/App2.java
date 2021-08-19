package com.java8.jpa.generator.impl;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.java8.jpa.generator.EntityGenerator;

@Mojo(name = "boot-generate", defaultPhase = LifecyclePhase.INITIALIZE)
public class App2 extends AbstractMojo
{

	private EntityGenerator generator = new SpringBootEntityGenerator();

	@Parameter(property = "templates")
	private String templates;

	@Parameter(property = "base")
	private String base;

	@Parameter(property = "fileLoc")
	private String fileLoc;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException 
	{
		boolean flag = true;
		try 
		{
			System.out.println("templates : " + templates);
			for(String filePath  : templates.split("\\|"))
			{
				System.out.println(fileLoc  + filePath + ".xml");
				if(filePath != null && !filePath.isEmpty())
				{
					generator.generate(fileLoc  + filePath + ".xml", base, flag);
					flag = false;
				}
					
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new MojoFailureException(e.getMessage());
		}
		finally 
		{
			generator = null;
			templates = null;
			base = null;
		}
	}
}

package com.java8.jpa.generator.impl;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.java8.jpa.generator.EntityGenerator;

@Mojo(name = "jpa-generate", defaultPhase = LifecyclePhase.INITIALIZE)
public class App extends AbstractMojo
{

	private EntityGenerator generator = new JpaEntityGenerator();
	
	@Parameter(property = "templates")
	private String[] templates;

	@Parameter(property = "base")
	private String base;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException 
	{
		boolean flag = true;
		try 
		{
			for(String filePath  : templates)
			{
				generator.generate(filePath, base, flag);
				flag = false;
			}
		}
		catch (Exception e) 
		{
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

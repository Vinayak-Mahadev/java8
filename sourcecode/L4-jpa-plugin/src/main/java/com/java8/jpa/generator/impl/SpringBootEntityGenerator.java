package com.java8.jpa.generator.impl;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.java8.jpa.generator.AbstractEntityGenerator;
import com.java8.jpa.generator.beans.Entity;

public class SpringBootEntityGenerator extends AbstractEntityGenerator
{
	private String packageName;
	public boolean generate(String input, String base, boolean flag) throws EntityGeneratorException
	{
		List<Element> entityElements = null;
		String outputPath = base; 
		String packageName = null;
		try 
		{
			entityElements = new ArrayList<Element>();

			Element doc = ((Element) convertStringToXMLDocument(getFileContent(input)).getLastChild());
			packageName = doc.getElementsByTagName(PACKAGE).item(0).getTextContent();
			
			if(this.packageName == null)
			{
				this.packageName = packageName;
				flag = true;
			}
			else if(this.packageName.equals(packageName))
			{
				flag = false;
			}
			else 
				flag = true;
			
			outputPath = outputPath + "/" + stringReplacer.apply(packageName, ".", "/");

			if(flag)
			{
				File file = new File(outputPath);
				if(file.exists() && file.isDirectory())
				{
					if(file.listFiles().length != 0)
					{
						for(File temp : file.listFiles())
						{
							System.out.println("Deleted : " + file.getPath());
							temp.delete();
						}
					}
				}
				else
				{
					System.out.println("Dir created : " + file.getPath());
					file.mkdirs();
				}
			}

			List<String> imports = new ArrayList<String>();
			NodeList defaultImport  = doc.getElementsByTagName(DEFAULT_IMPORT);
			for (int i = 0; i < defaultImport.getLength(); i++) 
				if(defaultImport.item(i).getTextContent() != null && !defaultImport.item(i).getTextContent().isEmpty())
					imports.add(defaultImport.item(i).getTextContent());

//			System.out.println("Default imports :: " + imports);

			NodeList nodeList  = doc.getElementsByTagName(ENTITY);
			for (int i = 0; i < nodeList.getLength(); i++) 
				entityElements.add((Element) nodeList.item(i));

			System.out.println("-----------------------------------------------------------------------------------------");
			AtomicInteger integer = new AtomicInteger(1);
			for(Entity entity : loadEntities(entityElements, packageName, new ArrayList<String>(imports), new Boolean(false)))
			{
				//				System.out.println(entity.getJavaFile());
				FileWriter writer = new FileWriter(outputPath +"/"+ entity.getClassName() +".java");
				writer.write(entity.getJavaFile());
				writer.close();
				System.out.println(integer.getAndIncrement() + ". "+ entity.getClassName() + " Entity Created.");
			}
			System.out.println("-----------------------------------------------------------------------------------------");

			return false;

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			if(packageName != null)
			{
				outputPath = base + "/" + stringReplacer.apply(packageName, ".", "/");
				File file = new File(outputPath);
				if(file.exists() && file.isDirectory())
				{
					if(file.listFiles().length != 0)
					{
						for(File temp : file.listFiles())
							temp.delete();
					}
				}
			}
			throw new EntityGeneratorException(e.getMessage(),e);
		}
		finally 
		{
			if(entityElements != null)
				entityElements.clear();
			entityElements = null;
		}
	}
}

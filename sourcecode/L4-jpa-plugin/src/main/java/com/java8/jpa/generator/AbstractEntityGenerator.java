package com.java8.jpa.generator;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.java8.jpa.generator.beans.Attributes;
import com.java8.jpa.generator.beans.Entity;

public abstract class AbstractEntityGenerator implements EntityGenerator 
{
	protected static String ID = "id";
	protected static String NAME = "name";
	protected static String SCOPE = "scope";
	protected static String TYPE = "type";
	protected static String FETCH = "fetch";
	protected static String SETTER = "setter";
	protected static String GETTER = "getter";
	protected static String ENQUIRER = "enquirer";
	protected static String ADDER = "adder";
	protected static String REMOVER = "remover";
	protected static String JAVA_ANNOTATION = "java-annotation";
	protected static String JAVA_ANNOTATION_S = "java-annotations";
	protected static String JOIN_COLUMN = "join-column";
	protected static String NULLABLE = "nullable";
	protected static String COLUMN = "column";
	protected static String OPTIONAL = "optional";
	protected static String MAPPED_BY = "mapped-by";
	protected static String PACKAGE = "package";
	protected static String ENTITY = "entity";
	protected static String ELEMENT = "Element";
	protected static String SEQUENCE_NAME = "sequence-name";
	protected static String ALLOCATION_SIZE = "allocation-size";
	protected static String INITIAL_VALUE = "initial-value";
	protected static String TEMPORAL = "temporal";
	protected static String SEQUENCE_GENERATOR = "sequence-generator";
	protected static String GENERATED_VALUE= "generated-value";
	protected static String GENERATOR= "generator";
	protected static String STRATEGY= "strategy";
	protected static String JAVA_ATTRIBUTES= "java-attributes";

	public boolean generate(Map<String,String> fileNameAndLocation) throws EntityGeneratorException
	{
		return false;
	}

	protected TriFunction<String, String, String, String> stringReplacer = (str,split,delimiter) -> 
	{
		StringTokenizer tokenizer = new StringTokenizer(str, split);
		StringJoiner joiner = new StringJoiner(delimiter);
		while (tokenizer.hasMoreTokens()) 
			joiner.add(tokenizer.nextToken());
		return joiner.toString();
	};
	
	protected List<Entity> loadEntities(List<Element> entityElements, String packageName) throws Exception
	{
		List<Entity> entities = new ArrayList<Entity>();

		for (Element entityElement : entityElements) 
		{
			Entity entity = new Entity();
			entity.setClassName(entityElement.getAttribute("class"));
			entity.setPackageName(packageName);

			NodeList entityChildList = entityElement.getChildNodes();

			for (int i = 0; i < entityChildList.getLength(); i++) 
			{
				Node childElement =  entityChildList.item(i);
				if(childElement == null || !childElement.getClass().getName().contains(ELEMENT)) continue;

				else if(childElement.getNodeName()!= null && childElement.getNodeName().equalsIgnoreCase(JAVA_ANNOTATION))
				{
					NodeList javaAnnoList = childElement.getChildNodes();
					for (int j = 0; j < javaAnnoList.getLength(); j++) 
					{
						Node javaAnn = javaAnnoList.item(i);
						if(javaAnn != null && javaAnn.getTextContent() != null)
							entity.getJavaAnnotations().add(javaAnn.getTextContent());
					}
				}
				else if(childElement.getNodeName()!= null && childElement.getNodeName().equalsIgnoreCase("table"))
				{
					Element element = (Element) childElement;
					if(element == null || !element.getClass().getName().contains(ELEMENT)) continue;
					if(element.getAttribute(NAME) != null)
						entity.setTableName(element.getAttribute(NAME));
					else
						entity.setTableName(entity.getClassName().toLowerCase());
				}
				else if(childElement.getNodeName()!= null && childElement.getNodeName().equalsIgnoreCase("attributes"))
				{
					Element attrElement = (Element) childElement;
					if(attrElement == null || !attrElement.getClass().getName().contains(ELEMENT)) continue;

					NodeList attrList = attrElement.getChildNodes();
					for (int j = 0; j < attrList.getLength(); j++) 
					{
						Node element = attrList.item(j);
						if(element == null || !element.getClass().getName().contains(ELEMENT)) continue;

						Attributes attributes = new Attributes();
						attributes.setEntityName(entity.getClassName());
						attributes.setAttributesType(element.getNodeName().trim());
						attributes.setPackageName(entity.getPackageName());

						attributes.setName(((Element) element).getAttribute(NAME).trim());
						attributes.setFetchType(((Element) element).getAttribute(FETCH).trim());
						attributes.setOptional(((Element) element).getAttribute(OPTIONAL).trim());
						attributes.setMappedBy(((Element) element).getAttribute(MAPPED_BY).trim());
						NodeList attrChildList = element.getChildNodes();

						for (int l = 0; l < attrChildList.getLength(); l++)
						{
							if(attrChildList.item(l) == null || !attrChildList.item(l).getClass().getName().contains(ELEMENT)) continue;

							Element attrChildElement = (Element) attrChildList.item(l);
							if(attrChildElement.getNodeName().equals(COLUMN))
							{
								attributes.setColumnName((((Element) attrChildElement).getAttribute(NAME)));
								attributes.setColumnNullable(((Element) attrChildElement).getAttribute(NULLABLE));
							}
							else if(attrChildElement.getNodeName().equals(JOIN_COLUMN))
							{
								attributes.setColumnName((((Element) attrChildElement).getAttribute(NAME)));
								attributes.setColumnNullable(((Element) attrChildElement).getAttribute(NULLABLE));
								attributes.setColumnType(true);
							}
							else if(attrChildElement.getNodeName().equals(JAVA_ANNOTATION_S))
							{
								collectEntityJavaAnnotations(attrChildElement, entity, attributes);
							}
							else if(attrChildElement.getNodeName().equals(JAVA_ATTRIBUTES))
							{
								collectFieldJavaAttributes(attrChildElement, entity, attributes);
							}
							else if(attrChildElement.getNodeName().equals(GENERATED_VALUE))
							{
								attributes.setGenerator(attrChildElement.getAttribute(GENERATOR));
								attributes.setGeneratorStrategy(attrChildElement.getAttribute(STRATEGY));
							}
							else if(attrChildElement.getNodeName().equals(SEQUENCE_GENERATOR))
							{
								collectSequenceGenerator(attrChildElement, entity, attributes);
							}
							else if(attrChildElement.getNodeName().contains(TEMPORAL))
								attributes.setTemporal(attrChildElement.getTextContent());
						}
						//						System.out.println(attrList.item(j).getClass());
						if(attributes != null)
							entity.getAttributes().add(attributes);
					}
				}
			}
			entities.add(entity);
		}
		return entities;
	}

	protected void collectEntityJavaAnnotations(Element attrChildElement, Entity entity, Attributes attributes)
	{
		NodeList annoList = attrChildElement.getElementsByTagName(JAVA_ANNOTATION);
		for (int k = 0; k < annoList.getLength(); k++) 
			if(annoList.item(k) != null)
				attributes.getJavaAnnotations().add(annoList.item(k).getTextContent());

	}

	protected void collectSequenceGenerator(Element attrChildElement, Entity entity, Attributes attributes)
	{
		attributes.setSequenceGenerator(attrChildElement.getAttribute(NAME));
		attributes.setSequenceGeneratorName(attrChildElement.getAttribute(SEQUENCE_NAME));
		attributes.setSequenceGeneratorAllocation(attrChildElement.getAttribute(ALLOCATION_SIZE));
		attributes.setSequenceGeneratorInitial(attrChildElement.getAttribute(INITIAL_VALUE));
	}

	protected void collectFieldJavaAttributes(Element attrChildElement, Entity entity, Attributes attributes)
	{
		attributes.setType(attrChildElement.getAttribute(TYPE));
		attributes.setScope(attrChildElement.getAttribute(SCOPE));

		if(attributes.getAttributesType().equals(ID))
			entity.setPrimaryType(attributes.getType());
		if(attributes.getType().contains("."))
			attributes.setPackageName("");

		NodeList attributesList = attrChildElement.getChildNodes();
		for (int k = 0; k < attributesList.getLength(); k++) 
		{
			if(attributesList.item(k) == null || !attributesList.item(k).getClass().getName().contains(ELEMENT)) continue;

			Element javaAttrElement = (Element) attributesList.item(k);

			if(javaAttrElement.getTagName().equals(GETTER))
			{
				attributes.setGetter(true);
				if(!javaAttrElement.getAttribute(NAME).isEmpty())
					attributes.setGetterName(javaAttrElement.getAttribute(NAME));
				if(!javaAttrElement.getAttribute(SCOPE).isEmpty())
					attributes.setGetterScope(javaAttrElement.getAttribute(SCOPE));

				attributes.getGetterAnnotations().addAll(getJavaAnnotation(javaAttrElement));
			}
			else if(javaAttrElement.getTagName().equals(SETTER))
			{
				attributes.setSetter(true);
				if(!javaAttrElement.getAttribute(NAME).isEmpty())
					attributes.setSetterName(javaAttrElement.getAttribute(NAME));
				if(!javaAttrElement.getAttribute(SCOPE).isEmpty())
					attributes.setSetterScope(javaAttrElement.getAttribute(SCOPE));

				attributes.getSetterAnnotations().addAll(getJavaAnnotation(javaAttrElement));
			}
			else if(javaAttrElement.getTagName().equals(ENQUIRER))
			{
				if(!javaAttrElement.getAttribute(NAME).isEmpty())
					attributes.setEnquirerName(javaAttrElement.getAttribute(NAME));
				attributes.getEnquirerAnnotations().addAll(getJavaAnnotation(javaAttrElement));

			}
			else if(javaAttrElement.getTagName().equals(ADDER))
			{
				if(!javaAttrElement.getAttribute(NAME).isEmpty())
					attributes.setAdderName(javaAttrElement.getAttribute(NAME));
				attributes.getAdderAnnotations().addAll(getJavaAnnotation(javaAttrElement));

			}
			else if(javaAttrElement.getTagName().equals(REMOVER))
			{
				if(!javaAttrElement.getAttribute(NAME).isEmpty())
					attributes.setRemoverName(javaAttrElement.getAttribute(NAME));
				attributes.getRemoverAnnotations().addAll(getJavaAnnotation(javaAttrElement));
			}

		}

	}

	protected List<String> getJavaAnnotation(Element element)
	{
		List<String> list = new ArrayList<String>();

		if(element == null) return list;

		NodeList nodeList = element.getElementsByTagName(JAVA_ANNOTATION);

		for (int i = 0; i < nodeList.getLength(); i++) 
		{
			if(nodeList.item(i) == null || !nodeList.item(i).getClass().getName().contains(ELEMENT)) continue;

			list.add(nodeList.item(i).getTextContent());

		}

		return list;
	}

	protected Document convertStringToXMLDocument(String xmlString) 
	{
		//Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		//API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try
		{
			//Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			//Parse the content to Document object
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	public String getFileContent(String path) throws IOException
	{
		StringJoiner joiner = new StringJoiner("\n");
		Files.readAllLines(new File(path).toPath())
		.stream()
		.map((s)-> s.replace("\t", "").replace("\r", ""))
		.forEach(joiner::add);
		return joiner.toString().replaceAll("<!--[\\s\\S]*?-->", "");
	}
}

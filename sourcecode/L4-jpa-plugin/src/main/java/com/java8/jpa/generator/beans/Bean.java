package com.java8.jpa.generator.beans;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Bean 
{
	private String packageName;
	
	private String outputPath;
	
	private String fullPath;
	
	private List<String> entityNameList;
	
	private Map<String, Entity> entityMap;
}

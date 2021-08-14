package com.java8.jpa.generator.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class Attributes 
{
	private String entityName;

	private String packageName;

	private String attributesType;

	private List<String> javaAnnotations = new ArrayList<String>(4);

	private String columnName;

	private boolean columnType;

	private String columnNullable;

	private String name;

	private String type;

	private String scope;

	private String temporal;

	private String mappedBy;

	private String fetchType;

	private String optional;

	private String generator;

	private String generatorStrategy;

	private String sequenceGenerator;

	private String sequenceGeneratorName;

	private String sequenceGeneratorAllocation;

	private String sequenceGeneratorInitial;

	private boolean getter;

	private String getterName;

	private String getterScope;

	private List<String> getterAnnotations = new ArrayList<String>(4);

	private boolean setter;

	private String setterName;

	private String setterScope;

	private List<String> setterAnnotations = new ArrayList<String>(4);

	private String enquirerName;

	private List<String> enquirerAnnotations = new ArrayList<String>(4);

	private String adderName;

	private List<String> adderAnnotations = new ArrayList<String>(4);

	private String removerName;

	private List<String> removerAnnotations = new ArrayList<String>(4);

	public StringJoiner getWriteableFields(StringJoiner joiner)
	{

		defalutAnnotations(joiner);

		column(joiner);

		generator(joiner);

		sequenceGenerator(joiner);

		if(getAttributesType().equalsIgnoreCase(ID))
			id(joiner);
		else if(getAttributesType().equalsIgnoreCase(BASIC))
			basic(joiner);
		else if(getAttributesType().equalsIgnoreCase(VERSION))
			version(joiner);
		else if(getAttributesType().equalsIgnoreCase(ONE_TO_MANY)) 	
			oneToMany(joiner);
		else if(getAttributesType().equalsIgnoreCase(MANY_TO_ONE))	    
			manyToOne(joiner);
		else if(getAttributesType().equalsIgnoreCase(MANY_TO_MANY))	
			manyToMany(joiner);
		else if(getAttributesType().equalsIgnoreCase(ONE_TO_ONE))      
			oneToOne(joiner);


		return joiner;
	}


	public StringJoiner getWriteableIDAdapter(StringJoiner joiner)
	{
		if(!getAttributesType().equalsIgnoreCase(ID)) return joiner;

		String setter = (getSetterName() != null && !getSetterName().isEmpty()) ? getSetterName() : "set"+ ("" + getName().charAt(0)).toUpperCase() + getName().substring(1, getName().length());
		String getter = (getGetterName() != null && !getGetterName().isEmpty()) ? getGetterName() : "get"+ ("" + getName().charAt(0)).toUpperCase() + getName().substring(1, getName().length());
		
		String classRef = ("" + getEntityName().charAt(0)).toLowerCase() + getEntityName().substring(1, getEntityName().length());
		
		joiner.add(TAB + "@XmlTransient");
		joiner.add(TAB + "public static class IDAdapter extends XmlAdapter<"+getType()+","+getEntityName()+">");
		joiner.add(TAB + "{");

		joiner.add(TAB + TAB+ "@Override");
		joiner.add(TAB + TAB+ "public "+getType()+" marshal("+getEntityName()+" "+classRef+") throws Exception");
		joiner.add(TAB + TAB+ "{");
		joiner.add(TAB + TAB+ TAB + "return "+classRef+"."+getter+"();");
		joiner.add(TAB + TAB+ "}");

		joiner.add("");

		joiner.add(TAB + TAB+ "@Override");
		joiner.add(TAB + TAB+ "public "+getEntityName()+" unmarshal("+getType()+" arg) throws Exception");
		joiner.add(TAB + TAB+ "{");
		joiner.add(TAB + TAB+ TAB + getEntityName() + " " + classRef + " = new " + getEntityName() +"();");
		joiner.add(TAB + TAB+ TAB +classRef+ "." + setter + "(arg);");
		joiner.add(TAB + TAB+ TAB + "return "+classRef+";");
		joiner.add(TAB + TAB+ "}");
		joiner.add(TAB + "}");

		joiner.add("");

		return joiner;
	}

	public StringJoiner getWriteableListener(StringJoiner joiner)
	{
		if(!getAttributesType().equalsIgnoreCase(ONE_TO_MANY)) return joiner;

		
		String name = null;
		if(getType().contains("."))
		{
			name = getType();
		}
		else
			name = getPackageName()+"."+getType();

		String classRef = ("" + getName().charAt(0)).toUpperCase() + getName().substring(1, getName().length());

		joiner.add(TAB + "@XmlTransient");
		joiner.add(TAB + "public static class "+classRef+"Listener");
		joiner.add(TAB + "{");

		joiner.add(TAB + TAB+ "@PrePersist");
		joiner.add(TAB + TAB+ "public void onPersist("+name+" arg)");
		joiner.add(TAB + TAB+ "{");
		joiner.add(TAB + TAB+ TAB + getEntityName()+" obj = arg.get"+getEntityName()+"();");
		joiner.add(TAB + TAB+ TAB + "obj._add"+classRef+"(arg);");
		joiner.add(TAB + TAB+ "}");

		joiner.add("");

		joiner.add(TAB + TAB+ "@PreRemove");
		joiner.add(TAB + TAB+ "public void onRemove("+name+" arg)");
		joiner.add(TAB + TAB+ "{");
		joiner.add(TAB + TAB+ TAB + getEntityName()+" obj = arg.get"+getEntityName()+"();");
		joiner.add(TAB + TAB+ TAB + "obj._remove"+classRef+"(arg);");
		joiner.add(TAB + TAB+ "}");
		joiner.add(TAB + "}");

		joiner.add("");

		joiner.add(TAB + "void _add"+classRef+"("+name+" arg)");
		joiner.add(TAB + "{");
		joiner.add(TAB + TAB + "this." + getName() +".add(arg);");
		joiner.add(TAB + "}");
		joiner.add("");

		joiner.add(TAB + "void _remove"+classRef+"("+name+" arg)");
		joiner.add(TAB + "{");
		joiner.add(TAB + TAB + "this." + getName() +".remove(arg);");
		joiner.add(TAB + "}");
		joiner.add("");

		return joiner;
	}

	public StringJoiner getWriteableSetters(StringJoiner joiner)
	{
		if(!isSetter()) return joiner;

		String scope = (getSetterScope() != null && !getSetterScope().isEmpty()) ? getSetterScope() : "public";
		String name = (getSetterName() != null && !getSetterName().isEmpty()) ? getSetterName() : "set"+ ("" + getName().charAt(0)).toUpperCase() + getName().substring(1, getName().length());

		getSetterAnnotations()
		.stream()
		.filter(a -> a != null && !a.trim().isEmpty())
		.forEach(a -> joiner.add(TAB + a));

		if(getAttributesType().equals(ONE_TO_MANY))
		{
			joiner.add(TAB + scope + " void " + name + "(java.util.Set<" + getType() + "> "+getName()+")");
			joiner.add(TAB + "{");
			joiner.add(TAB + TAB + "this." + getName() + " = "+getName()+";");
		}
		else if(getAttributesType().equals(MANY_TO_ONE))
		{
			joiner.add(TAB + scope + " void " + name + "(" + getType() + " "+getName()+")");
			joiner.add(TAB + "{");
			joiner.add(TAB + TAB + "if(this."+getName()+" == "+getName()+") return;");
			joiner.add("");
			joiner.add(TAB + TAB + "this." + getName() + " = "+getName()+";");
		}
		else
		{
			joiner.add(TAB + scope + " void " + name + "(" + getType() + " "+getName()+")");
			joiner.add(TAB + "{");
			joiner.add(TAB + TAB + "this." + getName() + " = "+getName()+";");
		}
		joiner.add(TAB + "}");
		joiner.add("");
		return joiner;
	}

	public StringJoiner getWriteableGetters(StringJoiner joiner)
	{
		if(!isGetter()) return joiner;

		String scope = (getGetterScope() != null && !getGetterScope().isEmpty()) ? getGetterScope() : "public";
		String name =(getGetterName() != null && !getGetterName().isEmpty()) ? getGetterName() : "get"+ ("" + getName().charAt(0)).toUpperCase() + getName().substring(1, getName().length());

		getGetterAnnotations()
		.stream()
		.filter(a -> a != null && !a.trim().isEmpty())
		.forEach(a -> joiner.add(TAB + a));

		if(getAttributesType().equals(ONE_TO_MANY))
		{
			joiner.add(TAB + scope + " java.util.Set<" + getPackageName() +"."+ getType() + "> " + name + "()");
			joiner.add(TAB + "{");
			joiner.add(TAB + TAB + "this." + getName() + ".size();");
			joiner.add(TAB + TAB + "return java.util.Collections.unmodifiableSet(this." + getName() +");");
		}
		else
		{
			joiner.add(TAB + scope + " " + getType() + " " + name + "()");
			joiner.add(TAB + "{");
			joiner.add(TAB + TAB + "return this." + getName() +";");
		}
		joiner.add(TAB + "}");
		joiner.add("");
		return joiner;
	}

	public StringJoiner getWriteableEnquirer(StringJoiner joiner)
	{
		if(getEnquirerName() == null || getEnquirerName().trim().isEmpty()) return joiner;

		String scope = (getGetterScope() != null && !getGetterScope().isEmpty()) ? getGetterScope() : "public";
		String name = (getEnquirerName() != null && !getEnquirerName().isEmpty()) ? getEnquirerName() : "has"+ ("" + getName().charAt(0)).toUpperCase() + getName().substring(1, getName().length());

		getEnquirerAnnotations()
		.stream()
		.filter(a -> a != null && !a.trim().isEmpty())
		.forEach(a -> joiner.add(TAB + a));

		if(getAttributesType().equals(ONE_TO_MANY))
		{
			joiner.add(TAB + scope + " Boolean "+name+"(" + getPackageName() +"."+ getType() + " arg" + ")");
			joiner.add(TAB + "{");
			joiner.add(TAB + TAB + "return this." + getName() + ".contains(arg);");
			joiner.add(TAB + "}");
		}
		else
		{
			joiner.add("// Needs to implement");
		}
		joiner.add("");
		return joiner;
	}

	public StringJoiner getWriteableAdder(StringJoiner joiner)
	{
		if(getAdderName() == null || getAdderName().trim().isEmpty()) return joiner;

		String scope = (getGetterScope() != null && !getGetterScope().isEmpty()) ? getGetterScope() : "public";
		String name = (getAdderName() != null && !getAdderName().isEmpty()) ? getAdderName() : "add"+ ("" + getName().charAt(0)).toUpperCase() + getName().substring(1, getName().length());

		getAdderAnnotations()
		.stream()
		.filter(a -> a != null && !a.trim().isEmpty())
		.forEach(a -> joiner.add(TAB + a));

		if(getAttributesType().equals(ONE_TO_MANY))
		{
			joiner.add(TAB + scope + " void "+name+"(" + getPackageName() +"."+ getType() + " arg" + ")");
			joiner.add(TAB + "{");
			joiner.add(TAB + TAB + "if(arg == null || this."+getName()+".contains(arg))");
			joiner.add(TAB + TAB + TAB + "return;");
			joiner.add(TAB + TAB + "this." + getName() + ".add(arg);");
			joiner.add(TAB + TAB + "arg.set" + getEntityName() + "(this);");
			joiner.add(TAB + "}");
		}
		else
		{
			joiner.add("// Needs to implement");
		}
		joiner.add("");
		return joiner;
	}

	public StringJoiner getWriteableRemove(StringJoiner joiner)
	{
		if(getAdderName() == null || getAdderName().trim().isEmpty()) return joiner;

		String scope = (getGetterScope() != null && !getGetterScope().isEmpty()) ? getGetterScope() : "public";
		String name = (getRemoverName() != null && !getRemoverName().isEmpty()) ? getRemoverName() : "remove"+ ("" + getName().charAt(0)).toUpperCase() + getName().substring(1, getName().length());

		getRemoverAnnotations()
		.stream()
		.filter(a -> a != null && !a.trim().isEmpty())
		.forEach(a -> joiner.add(TAB + a));

		if(getAttributesType().equals(ONE_TO_MANY))
		{
			joiner.add(TAB + scope + " void "+name+"(" + getPackageName() +"."+ getType() + " arg" + ")");
			joiner.add(TAB + "{");
			joiner.add(TAB + TAB + "if(arg == null || this."+getName()+".contains(arg))");
			joiner.add(TAB + TAB + TAB + "return;");
			joiner.add(TAB + TAB + "this." + getName() + ".remove(arg);");
			joiner.add(TAB + TAB + "arg.set" + getEntityName() + "(this);");
			joiner.add(TAB + "}");
		}
		else
		{
			joiner.add("// Needs to implement");
		}
		joiner.add("");
		return joiner;
	}


	protected void defalutAnnotations(StringJoiner joiner)
	{
		getJavaAnnotations()
		.stream()
		.filter(a -> a!=null)
		.map( a -> TAB + a)
		.forEach(joiner::add);
	}

	protected void column(StringJoiner joiner)
	{
		String column = "Column";
		if(isColumnType())
			column = "JoinColumn";

		if(getColumnNullable()!= null && !getColumnNullable().trim().isEmpty())
			joiner.add(TAB + "@"+column+"(name=\"" + getColumnName() + "\", nullable=" + getColumnNullable() + ")");	
		else if(getColumnName()!= null && !getColumnName().trim().isEmpty())
			joiner.add(TAB + "@"+column+"(name=\"" + getColumnName() + "\")");
	}

	protected void generator(StringJoiner joiner)
	{
		if(getGenerator() != null && !getGenerator().trim().isEmpty())
		{
			if(getGeneratorStrategy() != null && !getGeneratorStrategy().trim().isEmpty())
				joiner.add(TAB + "@GeneratedValue(generator=\"" + getGenerator() + "\"" + ",strategy=GenerationType." + getGeneratorStrategy() + ")");
			else
				joiner.add(TAB + "@GeneratedValue(generator=\"" + getGenerator() + "\"" + ")");
		}
	}

	protected void sequenceGenerator(StringJoiner joiner)
	{
		if(getSequenceGenerator() != null && !getSequenceGenerator().trim().isEmpty())
		{
			joiner.add(TAB + "@SequenceGenerator(allocationSize="+getSequenceGeneratorAllocation()+", initialValue="+getSequenceGeneratorInitial()+", name=\""+getSequenceGenerator()+"\", sequenceName=\""+getSequenceGeneratorName()+"\")");
		}
	}

	protected void id(StringJoiner joiner)
	{
		joiner.add(TAB + "@Id");
		joiner.add(TAB+ getScope() + " " + getType() + " " + getName() + ";");
	}

	protected void basic(StringJoiner joiner)
	{
		joiner.add(TAB + "@Basic");
		joiner.add(TAB + getScope() + " " + getType() + " " + getName() + ";");
	}

	protected void version(StringJoiner joiner)
	{
		joiner.add(TAB + "@Version");
		if(getTemporal() != null)
			joiner.add(TAB + "@Temporal(TemporalType."+getTemporal()+")");
		joiner.add(TAB + getScope() + " " + getType() + " " + getName() + ";");
	}

	protected void oneToMany(StringJoiner joiner)
	{
		if(getOptional() != null && getOptional().trim().isEmpty())
			joiner.add(TAB + "@OneToMany(fetch=FetchType."+getFetchType()+", mappedBy=\""+getMappedBy()+"\""+ ")");
		else
			joiner.add(TAB + "@OneToMany(fetch=FetchType."+getFetchType()+", optional="+getOptional()+")");
		if(getScope() != null && getScope().trim().isEmpty())
			setScope("protected");
		if(getPackageName().isEmpty())
			joiner.add(TAB + getScope() + " java.util.Set<"+getType().trim()+"> "+getName()+" = new java.util.HashSet<>();");
		else 
			joiner.add(TAB + getScope() + " java.util.Set<"+getPackageName().trim()+"."+getType().trim()+"> "+getName()+" = new java.util.HashSet<>();");
	}

	protected void manyToOne(StringJoiner joiner)
	{
		if(getOptional() != null && getOptional().trim().isEmpty())
			joiner.add(TAB + "@ManyToOne(fetch=FetchType."+getFetchType() + ")");
		else
			joiner.add(TAB + "@ManyToOne(fetch=FetchType."+getFetchType()+", optional="+getOptional()+")");

		if(getScope() != null && getScope().trim().isEmpty())
			setScope("protected");
		if(getPackageName().isEmpty())
			joiner.add(TAB + getScope() + " " + getType().trim()+" "+getName()+";");
		else 
			joiner.add(TAB + getScope() +" "+ getPackageName().trim()+"."+getType().trim()+" "+getName()+";");
	}

	protected void manyToMany(StringJoiner joiner)
	{
		joiner.add(TAB_TODO);
	}

	protected void oneToOne(StringJoiner joiner)
	{
		joiner.add(TAB_TODO);
	}

	static final String ID = "id";
	static final String BASIC = "basic";
	static final String VERSION = "version";
	static final String ONE_TO_MANY = "one-to-many";
	static final String MANY_TO_ONE = "many-to-one";
	static final String MANY_TO_MANY = "many-to-many";
	static final String ONE_TO_ONE = "one-to-one";
	static final String TAB = "\t";
	static final String TAB_TODO = "\t// Needs to implement";


}

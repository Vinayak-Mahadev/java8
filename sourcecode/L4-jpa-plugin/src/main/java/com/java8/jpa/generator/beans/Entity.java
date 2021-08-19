package com.java8.jpa.generator.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Entity 
{
	public Entity(List<String> imports)
	{
		this.imports = imports;
	}

	private final List<String> defaultImports = Arrays.asList("import javax.persistence.*;", 
			"import javax.persistence.Version;",
			"import java.util.Date;",
			"import javax.xml.bind.annotation.*;",
			"import javax.xml.bind.annotation.adapters.*;",
			"import org.hibernate.envers.*;");


	private List<String> imports;

	private List<String> javaAnnotations; 

	private String className;

	private String packageName;

	private String tableName;

	private String primaryType;

	private boolean jpaOldType;

	private List<Attributes> attributes = new ArrayList<Attributes>();


	public String getPackage()
	{
		return "package " + this.packageName;
	}

	public List<String> getJavaAnnotations()
	{
		List<String> list = new ArrayList<>();
//		System.out.println("javaAnnotations : " + javaAnnotations);
		if(javaAnnotations != null)
			list.addAll(javaAnnotations);
		list.add("@Entity(name="+ "\"" + getPackageName() +"." + getClassName() + "\")");
		list.add("@Table(name="+ "\"" + getTableName() + "\")");
		list.add("@Access(AccessType.FIELD)");
		return list;
	}

	public String getJavaFile()
	{
		Random random = new Random();
		final StringJoiner joiner = new StringJoiner("\n");
		joiner.add(getPackage()+";");
		joiner.add("\n");

		if(getImports() != null)
			getImports().forEach(joiner::add);
		else
			getDefaultImports().forEach(joiner::add);

		joiner.add("\n");

		getJavaAnnotations().forEach(joiner::add);
		joiner.add("public class " + getClassName() + " implements java.io.Serializable");
		joiner.add("{");
		joiner.add("");

		if(isRandomSerial())
			joiner.add(TAB + "private static final long serialVersionUID = "+ (random.nextInt(100) * 1234) +"l;");
		else
			joiner.add(TAB + "private static final long serialVersionUID = "+ 1 +"l;");

		joiner.add("");

		if(isJpaOldType())
		{
			// IDAdapter
			attributes
			.stream()
			.forEach(a ->
			{
				a.getWriteableIDAdapter(joiner);
				a.getWriteableListener(joiner);
			});

			// setter, getter, enquirer, adder && remover
			attributes
			.stream()
			.forEach(a ->
			{
				a.getWriteableSetters(joiner);
				a.getWriteableGetters(joiner);
				a.getWriteableEnquirer(joiner);
				a.getWriteableAdder(joiner);
				a.getWriteableRemove(joiner);
			});
		}

		prepareFields(joiner, attributes);

		joiner.add("");

		joiner.add("}");
		return joiner.toString();
	}

	private StringJoiner prepareFields(final StringJoiner joiner, final List<Attributes> attributes)
	{
		if(attributes == null || attributes.isEmpty()) return joiner;
		attributes
		.stream()
		.filter(attr -> attr != null)
		.forEach((attr) ->
		{
			attr.getWriteableFields(joiner);
			joiner.add(TAB);

		});
		return joiner;
	}

	static final String TAB = "\t";
	static final String TAB_TODO = "\t// Needs to implement";

	protected boolean randomSerial;
}

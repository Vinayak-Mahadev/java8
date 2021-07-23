package com.java8.practices.advance.entities.single;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "person")
public class Person 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "per_seq")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "is_active")
	@ColumnDefault("true")
	private Boolean isActive;
}

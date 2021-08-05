package com.java8.practices.advance.entities.one2one;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wife")
@RequiredArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Wife 
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "wf_seq")
	private Long id;
	
	@Column(name = "name")
	@NonNull
	private String name;
	
	@OneToOne(mappedBy = "wife")
	@JsonIgnore
	private Husband husband;
	
}

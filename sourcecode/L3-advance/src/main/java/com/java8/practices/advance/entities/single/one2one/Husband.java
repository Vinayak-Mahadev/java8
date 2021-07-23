package com.java8.practices.advance.entities.single.one2one;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "husband")
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Husband 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "hus_seq")
	private Long id;
	
	@Column(name = "name")
	@NonNull
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "wife_id", referencedColumnName = "id")
	@NonNull
	private Wife wife;
}

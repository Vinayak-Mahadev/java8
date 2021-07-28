package com.java8.practices.advance.entities.single.one2many;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart")
@RequiredArgsConstructor
public class Cart 
{
	@Id
	@Column(name = "cart_id")
	@Getter
	@Setter
	private Long cartId;
	
	@Column(name = "cart_name")
	@NonNull
	@Getter
	@Setter
	private String cartName;
	
	@OneToMany(mappedBy = "cart")	
	@Getter
	@Setter
	private Set<Item> items;
}

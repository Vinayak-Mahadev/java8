package com.java8.practices.advance.entities.one2many;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item")
@RequiredArgsConstructor
@NoArgsConstructor
public class Item 
{
	@Id
	@Column(name = "item_id")
	@Getter
	@Setter
	@NonNull
	private Long itemId;
	
	@Column(name="item_name")
	@NonNull
	@Getter
	@Setter
	private String itemName;
	
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false, referencedColumnName = "cart_id")
	@JsonIgnore
	@Getter
	private Cart cart;
}

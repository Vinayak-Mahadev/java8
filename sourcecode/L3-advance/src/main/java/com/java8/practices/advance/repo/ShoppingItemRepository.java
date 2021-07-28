package com.java8.practices.advance.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java8.practices.advance.entities.single.one2many.Cart;
import com.java8.practices.advance.entities.single.one2many.Item;

@Repository
public interface ShoppingItemRepository extends JpaRepository<Item, Long>
{
	List<Item> findByItemName(String itemName);
	
	List<Item> findByCart(Cart cart);

	List<Item> findByItemId(Long itemId);
}

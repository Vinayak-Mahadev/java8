package com.java8.practices.advance.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.java8.practices.advance.entities.one2many.Cart;
import com.java8.practices.advance.entities.one2many.Item;

@Repository
@EnableTransactionManagement
public interface ShoppingItemRepository extends JpaRepository<Item, Long>
{
	List<Item> findByItemName(String itemName);
	
	List<Item> findByCart(Cart cart);

	List<Item> findByItemId(Long itemId);
}

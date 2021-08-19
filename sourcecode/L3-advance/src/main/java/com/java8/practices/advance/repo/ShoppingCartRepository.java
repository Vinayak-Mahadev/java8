package com.java8.practices.advance.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.java8.practices.advance.entities.one2many.Cart;

@Repository
@EnableTransactionManagement
public interface ShoppingCartRepository extends JpaRepository<Cart, Long>
{
	List<Cart> findByCartId(Long cartId);
	
}

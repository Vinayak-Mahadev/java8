package com.java8.practices.advance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java8.practices.advance.entities.single.one2many.Cart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<Cart, Long>
{

}
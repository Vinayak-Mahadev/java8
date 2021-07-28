package com.java8.practices.advance.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.java8.practices.advance.entities.single.one2many.Cart;
import com.java8.practices.advance.entities.single.one2many.Item;
import com.java8.practices.advance.exceptions.ApplicationException;
import com.java8.practices.advance.exceptions.ResourceNotFoundException;
import com.java8.practices.advance.repo.ShoppingCartRepository;
import com.java8.practices.advance.repo.ShoppingItemRepository;

@Service
public class ShoppingCartService 
{
	protected ShoppingCartRepository cartRepository;

	protected ShoppingItemRepository itemRepository;

	public ShoppingCartService(ShoppingCartRepository cartRepository, ShoppingItemRepository itemRepository) 
	{
		super();
		this.cartRepository = cartRepository;
		this.itemRepository = itemRepository;
	}

	public List<Cart> getAllCarts()
	{
		return cartRepository.findAll(Sort.by(Order.desc("cart_id")));
	}

	public Cart getCart(Long id)
	{
		return cartRepository.getOne(id);
	}

	public Cart createCart(Cart cart)
	{
		if(cartRepository.getOne(cart.getCartId()) != null)
			throw new IllegalStateException("This cart is already present");
		return cartRepository.saveAndFlush(cart);
	}
	
	public List<Cart> createCarts(List<Cart> carts)
	{
		if(carts == null || carts.isEmpty())
			throw new IllegalStateException("This cart is already present");
		return cartRepository.saveAll(carts).stream().collect(Collectors.toList());
	}

	public Cart updateCart(Long id, Cart cart) throws ApplicationException
	{
		if(id != cart.getCartId())
			throw new IllegalArgumentException("Path and Request body ids are mismatch");
		if(cartRepository.getOne(id) == null)
			throw new ResourceNotFoundException(1001l, "Given Cart id isn't present");
		return cartRepository.saveAndFlush(cart);
	}

	public Cart deleteCart(Long id) throws ApplicationException
	{
		Cart cart = null;
		if(id == null)
			throw new IllegalArgumentException("Given Cart id isn't present");
		if((cart = cartRepository.getOne(id)) == null)
			throw new ResourceNotFoundException(1001l, "Given Cart id isn't present");
		cartRepository.delete(cart);
		return cart;
	}

	public Set<Item> getItemsByCartId(Long id) throws ApplicationException
	{
		return cartRepository
				.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(1001l, "Given item id isn't present"))
				.getItems();
	}

	public Item getItem(Long id)
	{
		return itemRepository.getOne(id);
	}

	public Item createItem(Item item)
	{
		if(itemRepository.getOne(item.getItemId()) != null)
			throw new IllegalStateException("This item is already present");
		return itemRepository.saveAndFlush(item);
	}

	public Item updateItem(Long id, Item item) throws ApplicationException
	{
		if(id != item.getItemId())
			throw new IllegalArgumentException("Path and Request body ids are mismatch");
		if(cartRepository.getOne(id) == null)
			throw new ResourceNotFoundException(1001l, "Given item id isn't present");
		return itemRepository.saveAndFlush(item);
	}

	public Item deleteItem(Long id) throws ApplicationException
	{
		Item item = null;
		if(id == null)
			throw new IllegalArgumentException("Given cart id isn't present");
		if((item = itemRepository.getOne(id)) == null)
			throw new ResourceNotFoundException(1001l, "Given item id isn't present");
		itemRepository.delete(item);
		return item;
	}

	public Set<Item> addItemByCartid(Long id, List<Item> items) throws ApplicationException
	{
		Cart cart = null;
		if(id == null)
			throw new IllegalArgumentException("Given Cart id isn't present");
		if((cart = cartRepository.getOne(id)) == null)
			throw new ResourceNotFoundException(1001l, "Given Cart id isn't present");
		if(items == null || items.isEmpty()) return new HashSet<Item>();

		cart.getItems().addAll(items);

		cart = cartRepository.saveAndFlush(cart);
		return cart.getItems();
	}
}
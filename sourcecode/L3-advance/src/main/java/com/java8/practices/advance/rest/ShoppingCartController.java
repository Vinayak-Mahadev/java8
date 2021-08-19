package com.java8.practices.advance.rest;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java8.practices.advance.conf.aop.TrackTime;
import com.java8.practices.advance.entities.one2many.Cart;
import com.java8.practices.advance.entities.one2many.Item;
import com.java8.practices.advance.exceptions.ApplicationException;
import com.java8.practices.advance.service.AppServiceLayer;

@RestController
@RequestMapping("/api/shopping")
public class ShoppingCartController 
{

	@Autowired
	protected AppServiceLayer shoppingCartService;

	@GetMapping(path = "/carts", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<List<Cart>> getAllCarts() throws ApplicationException
	{
		return ResponseEntity.ok(shoppingCartService.getAllCarts());
	}

	@GetMapping(path = "/cart/{id}", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Cart> getCart(@PathVariable(value = "id") Long id) throws ApplicationException
	{
		return ResponseEntity.ok(shoppingCartService.getCart(id));
	}

	@PostMapping(path = "/cart", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Cart> createCart(Cart cart) throws ApplicationException
	{
		return ResponseEntity.ok(shoppingCartService.createCart(cart));
	}

	@PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Cart> updateCart(@PathVariable(value = "id") Long id, Cart cart) throws ApplicationException
	{
		return ResponseEntity.ok(shoppingCartService.updateCart(id, cart));
	}

	@DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Cart> deleteCart(@PathVariable(value = "id") Long id) throws ApplicationException
	{
		return ResponseEntity.ok(shoppingCartService.deleteCart(id));
	}

	@GetMapping(path = "/items/{id}", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Set<Item>> getItemsByCartId(@PathVariable(value = "id") Long id) throws ApplicationException
	{
		return ResponseEntity.ok(shoppingCartService.getItemsByCartId(id));
	}

	@GetMapping(path = "/item/{id}", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Item> getItem(@PathVariable(value = "id") Long id) throws ApplicationException
	{
		return ResponseEntity.ok(shoppingCartService.getItem(id));
	}

	@PostMapping(path = "/cart/item", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Item> createItem(Item item) throws ApplicationException
	{
		return ResponseEntity.ok(shoppingCartService.createItem(item));
	}

	@PutMapping(path = "/item/{id}", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Item> updateItem(@PathVariable(value = "id") Long id, Item item) throws ApplicationException
	{
		return ResponseEntity.ok(shoppingCartService.updateItem(id, item));
	}

	@DeleteMapping(path = "/item/{id}", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Item> deleteItem(@PathVariable(value = "id") Long id) throws ApplicationException
	{
		return ResponseEntity.ok(shoppingCartService.deleteItem(id));
	}

	@GetMapping(path = "/loadDummy", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Void> loadDummy() throws ApplicationException
	{

		// [About collect (supplier, accumulator, combiner)]
		long count = 10; 
		long itemId = 1;
		for (long element = 1; element <= count; element++) 
		{
			Cart cart = new Cart();
			cart.setCartId(element);
			cart.setCartName("C:"+element);
			cart.setLastUpdatedTime(new Date());
			shoppingCartService.createCart(cart);
			
//			Set<Item> set = new HashSet<Item>();
			for (long l = element; l < 10; l++) 
			{
				Item item = new Item();
				item.setItemId(itemId++);
				item.setItemName("I:"+element);
				item.setLastUpdatedTime(new Date());
				item.setCart(cart);
				shoppingCartService.createItem(item);
			}
//			cart.setItems(set);
		}
		

		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}

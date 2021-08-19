package com.java8.practices.advance.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.java8.practices.advance.entities.one2many.Cart;
import com.java8.practices.advance.entities.one2many.Item;
import com.java8.practices.advance.entities.one2one.Husband;
import com.java8.practices.advance.entities.single.Person;
import com.java8.practices.advance.exceptions.ApplicationException;
import com.java8.practices.advance.exceptions.ResourceNotFoundException;
import com.java8.practices.advance.repo.HusbandWifeRepository;
import com.java8.practices.advance.repo.PersonRepository;
import com.java8.practices.advance.repo.ShoppingCartRepository;
import com.java8.practices.advance.repo.ShoppingItemRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope("prototype")
public class AppServiceLayer 
{
	@Autowired
	protected PersonRepository personRepository;

	@Autowired
	protected HusbandWifeRepository husbandWifeRepository;

	@Autowired
	protected ShoppingCartRepository cartRepository;

	@Autowired
	protected ShoppingItemRepository itemRepository;

	protected Function<Void, Long> REQ_ID = (o) ->  Long.parseLong(Thread.currentThread().getName());

	public List<Person> getAllPerson() throws ApplicationException
	{
		Long requestId = REQ_ID.apply(null);
		List<Person> list = null;
		try 
		{
			log.trace("Entry getAllPerson requestId : " + requestId);
			list = personRepository
					.findAll()
					.stream()
					.parallel()
					.filter(p -> p!=null && p.getIsActive())
					.sorted((p1,p2) -> p1.getId() > p2.getId() ? 1 : -1)
					.collect(Collectors.toCollection(ArrayList<Person>::new));
			return list;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
		finally 
		{
			log.trace("Exit getAllPerson list :: " + list);
			list = null;	
		}

	}

	public Person createPerson(Person person) throws ApplicationException
	{
		try 
		{
			return personRepository.save(person);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
	}

	public Person getPersonById(Long id) throws ApplicationException
	{
		try 
		{
			return personRepository
					.findById(id)
					.orElseThrow(
							()->
							new ResourceNotFoundException(1001l, "Given person id isn't present")
							);
		}
		catch (ApplicationException e) 
		{
			log.error(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
	}

	public Person updatePerson(Long id, Person person) throws ApplicationException
	{
		Person personInRep = null;
		try 
		{
			if(id != person.getId())
				throw new IllegalArgumentException("Path and Request body ids are mismatch");
			personInRep = personRepository
					.findById(id)
					.orElseThrow(
							()->
							new ResourceNotFoundException(1001l, "Given person id isn't present")
							);
			return personRepository.saveAndFlush(person);
		}
		catch (ApplicationException e) 
		{
			log.error(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
		finally
		{
			log.trace("Person in Repo {} ", personInRep);
			personInRep = null;
		}
	}

	public Person deletePerson(Long id) throws ApplicationException
	{
		Person person = null;
		try 
		{
			person = personRepository
					.findById(id)
					.orElseThrow(
							()->
							new ResourceNotFoundException(1001l, "Given person id isn't present")
							);
			personRepository.deleteById(person.getId());
			return person;
		}
		catch (ApplicationException e) 
		{
			log.error(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
		finally
		{
			log.trace("Person in Repo {} ", person);
			person = null;
		}
	}

	public Void createPersonPerson(List<Person> persons) throws ApplicationException
	{
		try
		{
			personRepository.saveAll(persons);
			return null;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
	}

	public List<Husband> getAllHusbandWife() throws ApplicationException 
	{
		List<Husband> list =  null; 
		try 
		{
			list = husbandWifeRepository
					.findAll()
					.stream()
					.parallel()
					//				.filter(hw -> hw != null && hw.getWife() != null)
					.sorted((h1,h2) -> (h1.getId() > h2.getId()) ? 1 : -1)
					.collect(Collectors.toList());
			return list;
		} 
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
		finally 
		{
			list = null;	
		}
	}

	public Husband createHusband(Husband husband) throws ApplicationException
	{
		Husband husbandCopy = null;
		try 
		{
			if(husbandWifeRepository.findById(husband.getId()).isPresent())
				throw new ResourceNotFoundException(1001l, "Given husband id is already present");
			husbandCopy = husbandWifeRepository.save(husband);
			return husbandCopy;
		} 
		catch (ApplicationException e) 
		{
			log.error(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
		finally 
		{
			husbandCopy = null;	
		}
	}

	public Husband getHusbandById(Long id) throws ApplicationException
	{
		Husband husband = null;
		try 
		{
			husband = husbandWifeRepository
					.findById(id)
					.orElseThrow(
							()->
							new ResourceNotFoundException(1001l, "Given husband id isn't present")
							);
			return husband;
		} 
		catch (ApplicationException e) 
		{
			log.error(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
		finally 
		{
			husband = null;	
		}
	}

	public Husband updateHusband(Long id, Husband husband) throws ApplicationException
	{
		Husband husbandCopy = null;
		try 
		{
			if(id != husband.getId())
				throw new IllegalArgumentException("Path and Request body ids are mismatch");
			husbandCopy = getHusbandById(id);
			return husbandWifeRepository.saveAndFlush(husband);
		} 
		catch (ApplicationException e) 
		{
			log.error(e.getMessage() + " {} ", husbandCopy, e);
			throw e;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() + " {} ", husbandCopy, e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
		finally 
		{
			husbandCopy = null;	
		}
	}

	public Husband deleteHusband(Long id) throws ApplicationException
	{
		Husband husband = null;
		try 
		{
			husband =  husbandWifeRepository
					.findById(id)
					.orElseThrow(()-> new ResourceNotFoundException(1001l, "Given husband id isn't present"));
			husbandWifeRepository.deleteById(id);
			return husband;

		}
		catch (ApplicationException e) 
		{
			log.error(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
		finally 
		{
			husband = null;	
		}
	}

	public Void createHusbandWife(List<Husband> husbands) throws ApplicationException
	{
		try 
		{
			husbandWifeRepository.saveAll(husbands);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
		return null;
	}

	public List<Cart> getAllCarts() throws ApplicationException
	{
		try 
		{
			return cartRepository.findAll(Sort.by(Order.desc("cartId")));

		}
		catch (Exception e) 
		{
			log.warn(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
	}

	public Cart getCart(Long id) throws ApplicationException
	{
		try 
		{
			return cartRepository
					.findById(id)
					.orElseThrow(
							()->
							new ResourceNotFoundException(1001l, "Given person id isn't present")
							);
		}
		catch (ApplicationException e) 
		{
			log.warn(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) 
		{
			log.warn(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
	}

	public Cart createCart(Cart cart) throws ApplicationException
	{
//		Cart temp = null;
		try 
		{
//			temp = cartRepository.getOne(cart.getCartId());
//			if(temp != null)
//			{
//				log.info("This cart is already present : " + temp);
//				throw new IllegalStateException("This cart is already present");
//			}
			return cartRepository.saveAndFlush(cart);
		}
		catch (Exception e) 
		{
			log.warn(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
		finally
		{
//			temp = null;
		}
	}

	public List<Cart> createCarts(List<Cart> carts) throws ApplicationException
	{
		try 
		{
			if(carts == null || carts.isEmpty())
				throw new IllegalStateException("This cart is already present");
			return cartRepository.saveAll(carts).stream().collect(Collectors.toList());
		}
		catch (Exception e) 
		{
			log.warn(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
	}

	public Cart updateCart(Long id, Cart cart) throws ApplicationException
	{
		try 
		{
			if(id != cart.getCartId())
				throw new IllegalArgumentException("Path and Request body ids are mismatch");
			if(cartRepository.getOne(id) == null)
				throw new ResourceNotFoundException(1001l, "Given Cart id isn't present");
			return cartRepository.saveAndFlush(cart);
		}
		catch (ApplicationException e) 
		{
			log.warn(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) 
		{
			log.warn(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
	}

	public Cart deleteCart(Long id) throws ApplicationException
	{
		try 
		{
			Cart cart = null;
			if(id == null)
				throw new IllegalArgumentException("Given Cart id isn't present");
			if((cart = cartRepository.getOne(id)) == null)
				throw new ResourceNotFoundException(1001l, "Given Cart id isn't present");
			cartRepository.delete(cart);
			return cart;
		}
		catch (ApplicationException e) 
		{
			log.warn(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) 
		{
			log.warn(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
	}

	public Set<Item> getItemsByCartId(Long id) throws ApplicationException
	{
		try 
		{
			return cartRepository
					.findById(id)
					.orElseThrow(()-> new ResourceNotFoundException(1001l, "Given item id isn't present"))
					.getItems();
		}
		catch (ApplicationException e) 
		{
			log.warn(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) 
		{
			log.warn(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
	}

	public Item getItem(Long id) throws ApplicationException
	{
		try 
		{
			return itemRepository.getOne(id);
		}
		catch (Exception e) 
		{
			log.warn(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
	}

	public Item createItem(Item item) throws ApplicationException
	{
		try 
		{
//			if(itemRepository.getOne(item.getItemId()) != null)
//				throw new IllegalStateException("This item is already present");
			return itemRepository.saveAndFlush(item);
		}
		catch (Exception e) 
		{
			log.warn(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
	}

	public Item updateItem(Long id, Item item) throws ApplicationException
	{
		try 
		{
			if(id != item.getItemId())
				throw new IllegalArgumentException("Path and Request body ids are mismatch");
			if(cartRepository.getOne(id) == null)
				throw new ResourceNotFoundException(1001l, "Given item id isn't present");
			return itemRepository.saveAndFlush(item);
		}
		catch (ApplicationException e) 
		{
			log.warn(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) 
		{
			log.warn(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
	}

	public Item deleteItem(Long id) throws ApplicationException
	{
		Item item = null;
		try 
		{
			if(id == null)
				throw new IllegalArgumentException("Given cart id isn't present");
			if((item = itemRepository.getOne(id)) == null)
				throw new ResourceNotFoundException(1001l, "Given item id isn't present");
			itemRepository.delete(item);
			return item;
		}
		catch (ApplicationException e) 
		{
			log.warn(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) 
		{
			log.warn(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
		finally
		{
			item = null;
		}
	}

	public Set<Item> addItemByCartid(Long id, List<Item> items) throws ApplicationException
	{
		Cart cart = null;
		try 
		{
			if(id == null)
				throw new IllegalArgumentException("Given Cart id isn't present");
			if((cart = cartRepository.getOne(id)) == null)
				throw new ResourceNotFoundException(1001l, "Given Cart id isn't present");
			if(items == null || items.isEmpty()) return new HashSet<Item>();

			cart.getItems().addAll(items);

			cart = cartRepository.saveAndFlush(cart);
			return cart.getItems();
		}
		catch (ApplicationException e) 
		{
			log.warn(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) 
		{
			log.warn(e.getMessage(),e);
			throw new ApplicationException(500l, e.getMessage(),e);
		}
		finally
		{
			cart = null;
		}
	}

}

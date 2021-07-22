package com.java8.practices.advance.rest;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java8.practices.advance.entities.single.Person;
import com.java8.practices.advance.repo.PersonRepository;

@RestController
@RequestMapping("/api")
public class PersonController 
{

	@Autowired
	PersonRepository personRepository;
	
	@GetMapping("/person")
	public ResponseEntity<Set<Person>> getAllPerson()
	{
		Set<Person> list = new HashSet<Person>();
		personRepository.findAll().forEach(list::add);
		return new ResponseEntity<Set<Person>>(list, HttpStatus.OK);
	}
}

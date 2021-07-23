package com.java8.practices.advance.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java8.practices.advance.entities.single.Person;
import com.java8.practices.advance.exceptions.ApplicationException;
import com.java8.practices.advance.exceptions.ResourceNotFoundException;
import com.java8.practices.advance.repo.PersonRepository;

@RestController
@RequestMapping("/api/person")
public class PersonController 
{

	@Autowired
	PersonRepository personRepository;

	@GetMapping(path = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<Person>> getAllPerson() throws ApplicationException
	{
		List<Person> list = personRepository
				.findAll()
				.stream()
				.parallel()
				.filter(p -> p!=null && p.getIsActive())
				.sorted((p1,p2) -> p1.getId() > p2.getId() ? 1 : -1)
				.collect(Collectors.toList());
		return ResponseEntity.ok(list);
		//		return new ResponseEntity<List<Person>>(list, HttpStatus.OK);
	}

	@PostMapping(path = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) throws ApplicationException
	{
		return ResponseEntity.ok().body(personRepository.save(person));
	}

	@GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Person> getPersonById(@PathVariable(name = "id") Long id) throws ApplicationException
	{
		return ResponseEntity.ok()
				.body(personRepository
						.findById(id)
						.orElseThrow(
								()->
								new ResourceNotFoundException(1001l, "Given person id isn't present")
								));
	}

	@PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Person> updatePerson(@PathVariable(name = "id") Long id, @Valid @RequestBody Person person) throws ApplicationException
	{
		if(id != person.getId())
			throw new IllegalArgumentException("Path and Request body ids are mismatch");
		ResponseEntity.ok()
		.body(personRepository
				.findById(id)
				.orElseThrow(
						()->
						new ResourceNotFoundException(1001l, "Given person id isn't present")
						));
		return ResponseEntity.ok().body(personRepository.saveAndFlush(person));
	}

	@DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Person> deletePerson(@PathVariable(name = "id") Long id) throws ApplicationException
	{
		Person person =  personRepository
				.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(1001l, "Given person id isn't present"));
		personRepository.deleteById(id);
		return ResponseEntity.ok(person);
	}

	@GetMapping(path = "/loadDummy", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Void> loadDummy() throws ApplicationException
	{
		// About collect (supplier, accumulator, combiner)
		long count = 10; 
		personRepository.saveAll(LongStream
				.range(0l, count)
				.sequential()
				.collect(
						ArrayList::new,
						(list, element) ->	list.add(new Person(element, "P:"+element, true)),
						ArrayList::addAll
						));
		//		.forEach(l -> personRepository.save(new Person(l, "P:"+l, true)));
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}

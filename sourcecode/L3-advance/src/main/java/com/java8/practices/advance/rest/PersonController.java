package com.java8.practices.advance.rest;

import java.util.ArrayList;
import java.util.List;
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

import com.java8.practices.advance.conf.aop.TrackTime;
import com.java8.practices.advance.entities.single.Person;
import com.java8.practices.advance.exceptions.ApplicationException;
import com.java8.practices.advance.service.AppServiceLayer;

@RestController
@RequestMapping("/api/person")
public class PersonController 
{

	@Autowired
	protected AppServiceLayer personRepository;

	@GetMapping(path = "/", produces = { MediaType.APPLICATION_JSON_VALUE /* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<List<Person>> getAllPerson() throws ApplicationException
	{
		return ResponseEntity.ok().body(personRepository.getAllPerson());
	}

	@PostMapping(path = "/", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) throws ApplicationException
	{
		return ResponseEntity.ok().body(personRepository.createPerson(person));
	}

	@GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Person> getPersonById(@PathVariable(name = "id") Long id) throws ApplicationException
	{
		return ResponseEntity.ok().body(personRepository.getPersonById(id));
	}

	@PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Person> updatePerson(@PathVariable(name = "id") Long id, @Valid @RequestBody Person person) throws ApplicationException
	{
		return ResponseEntity.ok().body(personRepository.updatePerson(id, person));
	}

	@DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Person> deletePerson(@PathVariable(name = "id") Long id) throws ApplicationException
	{
		return ResponseEntity.ok(personRepository.deletePerson(id));
	}

	@GetMapping(path = "/loadDummy", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Void> loadDummy() throws ApplicationException
	{
		// About collect (supplier, accumulator, combiner)
		long count = 10; 
		personRepository.createPersonPerson(LongStream
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

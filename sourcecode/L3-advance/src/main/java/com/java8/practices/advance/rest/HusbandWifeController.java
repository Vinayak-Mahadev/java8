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

import com.java8.practices.advance.entities.single.one2one.Husband;
import com.java8.practices.advance.entities.single.one2one.Wife;
import com.java8.practices.advance.exceptions.ApplicationException;
import com.java8.practices.advance.exceptions.ResourceNotFoundException;
import com.java8.practices.advance.repo.HusbandWifeRepository;

@RestController
@RequestMapping("/api/HusbandWife")
public class HusbandWifeController 
{
	@Autowired
	HusbandWifeRepository husbandWifeRepository;

	@GetMapping(path = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<Husband>> getAllHusbandWife()
	{
		List<Husband> list =  husbandWifeRepository
				.findAll()
				.stream()
				.parallel()
				//				.filter(hw -> hw != null && hw.getWife() != null)
				.sorted((h1,h2) -> (h1.getId() > h2.getId()) ? 1 : -1)
				.collect(Collectors.toList());

		return new ResponseEntity<List<Husband>>(list, HttpStatus.OK);
	}

	@PostMapping(path = "/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Husband> createPerson(@Valid @RequestBody Husband husband) throws ApplicationException
	{
		return ResponseEntity.ok().body(husbandWifeRepository.save(husband));
	}


	@GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Husband> getHusbandById(@PathVariable(value = "id") Long id) throws ApplicationException
	{
		//		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404l, "Given user id not present"));
		return ResponseEntity
				.ok(husbandWifeRepository
						.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException(1001l, "Given user id is not present")));
	}

	@PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Husband> updatePerson(@PathVariable(name = "id") Long id, @Valid @RequestBody Husband husband) throws ApplicationException
	{
		if(id != husband.getId())
			throw new IllegalArgumentException("Path and Request body ids are mismatch");
		ResponseEntity.ok()
		.body(husbandWifeRepository
				.findById(id)
				.orElseThrow(
						()->
						new ResourceNotFoundException(1001l, "Given husband id isn't present")
						));
		return ResponseEntity.ok().body(husbandWifeRepository.saveAndFlush(husband));
	}

	@DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Husband> deletePerson(@PathVariable(name = "id") Long id) throws ApplicationException
	{
		Husband husband =  husbandWifeRepository
				.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException(1001l, "Given husband id isn't present"));
		husbandWifeRepository.deleteById(id);
		return ResponseEntity.ok(husband);
	}


	@GetMapping(path = "/loadDummy", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Void> loadDummy() throws ApplicationException
	{
		// 1 way (Simple)
		/*
		LongStream
		.range(1, 11)
		.forEach(l -> husbandWifeRepository
				.save(new Husband("H"+l, new Wife("W"+l))));
		 */

		// 2 way [About collect (supplier, accumulator, combiner)]
		long count = 10; 
		husbandWifeRepository.saveAll(LongStream
				.range(0l,count)
				.sequential()
				.collect(
						ArrayList::new,
						(list, element) ->	list.add(new Husband("H"+element, new Wife("W"+element))),
						ArrayList::addAll
						));

		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}

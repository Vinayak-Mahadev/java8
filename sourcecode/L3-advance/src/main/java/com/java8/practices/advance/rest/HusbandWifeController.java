package com.java8.practices.advance.rest;

import java.util.ArrayList;
import java.util.Date;
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
import com.java8.practices.advance.entities.one2one.Husband;
import com.java8.practices.advance.entities.one2one.Wife;
import com.java8.practices.advance.exceptions.ApplicationException;
import com.java8.practices.advance.service.AppServiceLayer;

@RestController
@RequestMapping("/api/HusbandWife")
public class HusbandWifeController 
{
	@Autowired
	AppServiceLayer service;

	@GetMapping(path = "/", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<List<Husband>> getAllHusbandWife() throws ApplicationException
	{
		return new ResponseEntity<List<Husband>>(service.getAllHusbandWife(), HttpStatus.OK);
	}

	@PostMapping(path = "/", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Husband> createHusband(@Valid @RequestBody Husband husband) throws ApplicationException
	{
		return ResponseEntity.ok().body(service.createHusband(husband));
	}


	@GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Husband> getHusbandById(@PathVariable(value = "id") Long id) throws ApplicationException
	{
		//		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(404l, "Given user id not present"));
		return ResponseEntity.ok(service.getHusbandById(id));
	}

	@PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Husband> updateHusband(@PathVariable(name = "id") Long id, @Valid @RequestBody Husband husband) throws ApplicationException
	{
		return ResponseEntity.ok().body(service.updateHusband(id, husband));
	}

	@DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
	public ResponseEntity<Husband> deleteHusband(@PathVariable(name = "id") Long id) throws ApplicationException
	{
		return ResponseEntity.ok(service.deleteHusband(id));
	}

	@GetMapping(path = "/loadDummy", produces = {MediaType.APPLICATION_JSON_VALUE/* , MediaType.APPLICATION_XML_VALUE */})
	@TrackTime
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
		long count = 10l;

		return ResponseEntity.ok(service
				.createHusbandWife(
						LongStream
						.range(0l,count)
						.sequential()
						.collect(
								ArrayList::new,
								(list, element) ->	
								{
									Wife wife = new Wife();
									wife.setName("W" + element);
									wife.setLastUpdatedTime(new Date());
									Husband husband = new Husband();
									husband.setName("H" + element);
									husband.setLastUpdatedTime(new Date());
									husband.setWife(wife);
									list.add(husband);
								}
								,
								ArrayList::addAll
								)
						));
	}
}

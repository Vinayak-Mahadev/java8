package com.java8.practices.advance.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java8.practices.advance.entities.single.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long>
{

}

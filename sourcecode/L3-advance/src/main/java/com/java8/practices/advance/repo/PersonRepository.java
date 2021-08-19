package com.java8.practices.advance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.java8.practices.advance.entities.single.Person;

@Repository
@EnableTransactionManagement
public interface PersonRepository extends JpaRepository<Person, Long>
{

}

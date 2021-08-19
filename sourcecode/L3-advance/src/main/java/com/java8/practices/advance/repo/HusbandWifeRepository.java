package com.java8.practices.advance.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.java8.practices.advance.entities.one2one.Husband;

@Repository
@EnableTransactionManagement
public interface HusbandWifeRepository extends JpaRepository<Husband, Long>
{

}

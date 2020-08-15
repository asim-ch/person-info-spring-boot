package com.example.personinfo.repositories;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.personinfo.entities.Person;

/**
 * This is a person repository interface to handle all CRUD operations on person
 * entity.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Serializable> {
    Page<Person> findAll(Pageable pageable);
}

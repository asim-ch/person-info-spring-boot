package com.example.personinfo.repositories;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.personinfo.entities.Contact;

/**
 * This is a contact repository interface to handle all CRUD operations on
 * person entity.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
public interface ContactRepository extends JpaRepository<Contact, Serializable> {
    Contact findByPersonId(Integer personId);
}

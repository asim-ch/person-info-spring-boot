package com.example.personinfo.repositories;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.personinfo.entities.PersonContact;

/**
 * This is a person contact repository interface to handle all CRUD operations
 * on person contact entity.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
@Repository
public interface PersonContactRepository extends JpaRepository<PersonContact, Serializable> {
    List<PersonContact> findByPersonId(Integer personId);
}

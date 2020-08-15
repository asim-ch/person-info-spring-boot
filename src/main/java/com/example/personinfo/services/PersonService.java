package com.example.personinfo.services;

import java.util.List;
import com.example.personinfo.dtos.PersonDTO;

/**
 * This is the service interface to abstract operations for person object.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
public interface PersonService {

    public PersonDTO getPersonById(Integer personId);

    public boolean deletePersonById(Integer personId);

    public PersonDTO addPerson(PersonDTO person);

    public PersonDTO updatePerson(PersonDTO person);

    public List<PersonDTO> getAllPersons(Integer pageNumber, Integer pageSize);
}

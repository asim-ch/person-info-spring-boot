package com.example.personinfo.utils;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.personinfo.dtos.ContactDTO;
import com.example.personinfo.dtos.PersonDTO;
import com.example.personinfo.entities.Person;
import com.example.personinfo.entities.PersonContact;
import com.example.personinfo.services.impl.PersonServiceImpl;

/**
 * A common response handler utility class to build response objects in better form.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
public class ResponseHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

    /**
     * This method is used build a response object by filtering out unnecessary data.
     * 
     * @param person [Person] - person entity object
     * @param person [ModelMapper] - model mapper
     * @return personDTO [PersonDTO] - person DTO object
     */
    public static PersonDTO buildPersonResponse(Person person, ModelMapper mapper) {
        List<ContactDTO> contacts = new ArrayList<>();
        PersonDTO personDTO = new PersonDTO();

        try {
            personDTO = mapper.map(person, PersonDTO.class);
            if (person.getPersonContacts() != null) {
                person.getPersonContacts().forEach(personContact -> {
                    Person personObj = personContact.getContact().getPerson();
                    ContactDTO contactDTO = mapper.map(personObj, ContactDTO.class);
                    contactDTO.setPersonId(personObj.getId());
                    contacts.add(contactDTO);
                });
                personDTO.setContacts(contacts);
            }
        } catch (Exception e) {
            LOGGER.error("Error occured while building person API response, error: " + e.toString());
            throw e;
        }

        return personDTO;
    }

    /**
     * This method is used build a response object by filtering out unnecessary data.
     * 
     * @param person [Person] - person entity object
     * @param personContacts [List<PersonContact>] - list of person contacts entities
     * @param person [ModelMapper] - model mapper
     * @return personDTO [PersonDTO] - person DTO object
     */
    public static PersonDTO buildPersonResponse(Person person, List<PersonContact> personContacts, ModelMapper mapper) {
        List<ContactDTO> contacts = new ArrayList<>();
        PersonDTO personDTO = new PersonDTO();

        try {
            personDTO = mapper.map(person, PersonDTO.class);
            if (personContacts != null) {
                personContacts.forEach(personContact -> {
                    Person personObj = personContact.getContact().getPerson();
                    ContactDTO contactDTO = mapper.map(personObj, ContactDTO.class);
                    contactDTO.setPersonId(personObj.getId());
                    contacts.add(contactDTO);
                });
                personDTO.setContacts(contacts);
            }
        } catch (Exception e) {
            LOGGER.error("Error occured while building person API response, error: " + e.toString());
            throw e;
        }

        return personDTO;
    }
}

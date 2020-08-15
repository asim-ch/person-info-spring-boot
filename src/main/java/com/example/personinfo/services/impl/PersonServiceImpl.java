package com.example.personinfo.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.personinfo.entities.Contact;
import com.example.personinfo.entities.Person;
import com.example.personinfo.entities.PersonContact;
import com.example.personinfo.PersonInfoException;
import com.example.personinfo.dtos.ContactDTO;
import com.example.personinfo.dtos.CustomErrorDTO;
import com.example.personinfo.dtos.PersonDTO;
import com.example.personinfo.repositories.ContactRepository;
import com.example.personinfo.repositories.PersonContactRepository;
import com.example.personinfo.repositories.PersonRepository;
import com.example.personinfo.services.PersonService;
import com.example.personinfo.utils.ResponseHandler;

/**
* This is the service implementation class to perform all business logic related to
* persons and it's mainly used by controller to perform crud operations.
*
* @author  Asim shahzad
* @since   2020-08-15 
*/
@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    PersonContactRepository personContactRepository;

    @Autowired
    ModelMapper mapper;

    /**
     * This method is used to fetch the person based on person ID provided as an argument.
     * The resulted person object would also contain its contacts information. It's cacheable
     * method and uses person ID as a key to store the entry in cache.
     * 
     * @param personId [Integer] - person ID to be searched
     * @return personDTO [PersonDTO] - DTO holding person's information
     */
    @Override
    @Transactional
    @Cacheable(cacheNames="getPersonByIdCache", key="#personId")
    public PersonDTO getPersonById(Integer personId) {
        PersonDTO personDTO = new PersonDTO();

        try {
            Optional<Person> personObj = personRepository.findById(personId);
            if (personObj.isPresent()) {
                Person person = personObj.get();
                // For lazy loading, fetching contacts explicitly to avoid performance issues.
                List<PersonContact> personContacts = personContactRepository.findByPersonId(personId);
                personDTO = ResponseHandler.buildPersonResponse(person, personContacts, mapper);
            } else {
                throw new PersonInfoException(new CustomErrorDTO(HttpStatus.NOT_FOUND, "Person Not Found",
                    "Person with ID " + personId + " not found"));
            }
        } catch (Exception e) {
            if (e instanceof PersonInfoException) {
                LOGGER.debug(e.toString());
            } else {
                LOGGER.error("Error occured while saving a new person into DB, error: " + e.toString());
            }
            throw e;
        }

        return personDTO;
    }

    /**
     * This method is used to fetch all persons based on pagination parameters provided as arguments.
     * The resulted person objects would also contain their contacts information. It's cacheable
     * method and uses pageNumber as a key to store the entries in cache.
     * 
     * @param pageNumber [Integer] - page number to indicate the results page in DB.
     * @param pageSize [Integer] - page size to indicate how many results to fetch from the page.
     * @return personDTOs [List<personDTO>] - DTOs holding person information
     */
    @Override
    @Transactional
    @Cacheable(cacheNames="getAllPersonsCache", key="#pageNumber")
    public List<PersonDTO> getAllPersons(Integer pageNumber, Integer pageSize) {
        List<PersonDTO> personDTOs = new ArrayList<>();

        try {
            PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
            Page<Person> allPersons = personRepository.findAll(pageRequest);
            if (allPersons.getSize() > 0) {
                allPersons.stream().forEach(person -> {
                    // For lazy loading, fetching contacts explicitly to avoid performance issues.
                    PersonDTO personDTO = ResponseHandler.buildPersonResponse(person, 
                            personContactRepository.findByPersonId(person.getId()),
                            mapper);
                    personDTOs.add(personDTO);
                });
            }
        } catch (Exception e) {
            if (e instanceof PersonInfoException) {
                LOGGER.debug(e.toString());
            } else {
                LOGGER.error("Error occured while fetching all persons using pagination, error: " + e.toString());
            }
            throw e;
        }

        return personDTOs;
    }

    /**
     * This method is used to delete a person based on it's id provided as an argument.
     * It's uses cache to clear relevant entries from different chaches.
     * 
     * @param personId [Integer] - person ID to be deleted
     * @return result [boolean] - flag to indicate the success
     */
    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames="getPersonByIdCache", key="#personId"),
            @CacheEvict(cacheNames="getAllPersonsCache", allEntries=true)})
    public boolean deletePersonById(Integer personId) {
        boolean result = false;

        try {
            Optional<Person> personObj = personRepository.findById(personId);
            if (personObj.isPresent()) {
                personRepository.deleteById(personId);
                result = true;
            } else {
                throw new PersonInfoException(new CustomErrorDTO(HttpStatus.NOT_FOUND, "Not found",
                    "Person with ID " + personId + " not found"));
            }
        } catch (Exception e) {
            if (e instanceof PersonInfoException) {
                LOGGER.debug(e.toString());
            } else {
                LOGGER.error("Error occured while deleting the person, error: " + e.toString());
            }
            throw e;
        }

        return result;
    }

    /**
     * This is a private method and used by other methods inside this service to 
     * process common logic, it saves the person's contact information into DB.
     * 
     * @param contactDTOs [List<ContactDTO>] - a list of contact DTOs
     * @return contacts [List<Contact>] - a list of contact entities
     */
    @Transactional
    private List<Contact> saveContacts(List<ContactDTO> contactDTOs) {
        List<Contact> contacts = new ArrayList<>();

        try {
            contacts = contactDTOs.stream().map(contactDTO -> {
                Integer personId = contactDTO.getPersonId();
                Contact contact = new Contact();

                if (personId == null) {
                    throw new PersonInfoException(new CustomErrorDTO(HttpStatus.BAD_REQUEST, "Person ID in contacts is missing",
                            "Please provide a valid person ID in contacts"));
                } else {
                    Optional<Person> personObj = personRepository.findById(personId);

                    if (!personObj.isPresent()) {
                        throw new PersonInfoException(new CustomErrorDTO(HttpStatus.BAD_REQUEST, "Contact not found",
                                "Person with ID " + personId + " in contacts list is not present, please create a person first with this ID"));
                    }
                    contact = contactRepository.findByPersonId(personObj.get().getId());
                    if (contact == null) {
                        contact = mapper.map(contactDTO, Contact.class);
                        contact.setPerson(personObj.get());
                        contact = contactRepository.save(contact);
                    }
                }

                return contact;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            if (e instanceof PersonInfoException) {
                LOGGER.debug(e.toString());
            } else {
                LOGGER.error("Error occured while saving contacts into DB, error: " + e.toString());
            }
            throw e;
        }
        
        return contacts;
    }

    /**
     * This is a private method and used by other methods inside this service to 
     * process common logic, it fetches the person's contact information from DB.
     * 
     * @param personDTO [PersonDTO] - person DTO object
     * @param person [Person] - person entity object
     * @return personContacts [List<PersonContact>] - a list of person contact entities 
     */
    private List<PersonContact> getContacts(PersonDTO personDTO, Person person) {
        List<PersonContact> personContacts = new ArrayList<>();

        if (personDTO.getContacts() != null) {
            Set<Integer> filterDuplicateContacts = new HashSet<>();
            List<ContactDTO> contactDtos = personDTO.getContacts().stream()
                .filter(contact -> filterDuplicateContacts.add(contact.getPersonId()))
                .collect(Collectors.toList());
            List<Contact> contacts = saveContacts(contactDtos);
            
            for (Contact contact : contacts) {
                PersonContact personContact = new PersonContact();
                personContact.setContact(contact);
                personContact.setPerson(person);
                personContacts.add(personContact);
            }
        }

        return personContacts;
    }

    /**
     * This method is used to add a new person into DB. The person 
     * object would have all the information including it's contacts.
     * It's uses cache to clear/update relevant entries on different chaches.
     * 
     * @param personDTO [PersonDTO] - person DTO object
     * @return personDTO [PersonDTO] - updated person DTO object
     */
    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value="getAllPersonsCache", allEntries=true)},
            put={@CachePut(value="getPersonByIdCache", key="#result.id")})
    public PersonDTO addPerson(PersonDTO personDTO) {
        Person person = new Person();

        try {
            if (personDTO != null) {
                if (personDTO.getId() != null) {
                    Optional<Person> personObj  = personRepository.findById(personDTO.getId());
                    personObj.ifPresent(p -> {
                         throw new PersonInfoException(new CustomErrorDTO(HttpStatus.BAD_REQUEST, "Duplicate ID",
                                "Person with ID " + p.getId() + " already exists, please try another one"));
                    });
                }
                person = mapper.map(personDTO, Person.class);
                person.setPersonContacts(getContacts(personDTO, person));
                person = personRepository.save(person);
                personDTO = ResponseHandler.buildPersonResponse(person, mapper);
            }
        } catch (Exception e) {
            if (e instanceof PersonInfoException) {
                LOGGER.debug(e.toString());
            } else {
                LOGGER.error("Error occured while saving a new person into DB, error: " + e.toString());
            }
            throw e;
        }

        return personDTO;
    }

    /**
     * This method is used to update an existing person. The person object would
     * have all the information including its contacts. It's uses cache to
     * clear/update relevant entries on different caches.
     * 
     * @param personDTO
     *            [PersonDTO] - person DTO object
     * @return personDTO [PersonDTO] - updated person DTO object
     */
    @Override
    @Transactional
    @Caching(evict = { @CacheEvict(value = "getAllPersonsCache", allEntries = true) }, put = {
            @CachePut(value = "getPersonByIdCache", key = "#personDTO.id") })
    public PersonDTO updatePerson(PersonDTO personDTO) {

        try {
            if (personDTO != null && personDTO.getId() != null) {
                Optional<Person> personObj = personRepository.findById(personDTO.getId());
                if (personObj.isPresent()) {
                    Person person = mapper.map(personDTO, Person.class);
                    person.setPersonContacts(getContacts(personDTO, person));
                    person = personRepository.save(person);
                    personDTO = ResponseHandler.buildPersonResponse(person, mapper);
                } else {
                    throw new PersonInfoException(new CustomErrorDTO(HttpStatus.NOT_FOUND, "Not found",
                            "Person with ID " + personDTO.getId() + " not found"));
                }
            } else {
                throw new PersonInfoException(new CustomErrorDTO(HttpStatus.NOT_FOUND, "Missing Person ID",
                        "Please provide a valid person ID to update"));
            }
        } catch (Exception e) {
            if (e instanceof PersonInfoException) {
                LOGGER.debug(e.toString());
            } else {
                LOGGER.error("Error occured while updating the person into DB, error: " + e.toString());
            }
            throw e;
        }

        return personDTO;
    }
}

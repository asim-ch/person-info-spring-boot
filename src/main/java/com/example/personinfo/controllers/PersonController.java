package com.example.personinfo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.personinfo.dtos.PersonDTO;
import com.example.personinfo.services.PersonService;

/**
 * This is the main controller to handle requests for person CRUD operations.
 *
 * @author Asim shahzad
 * @since 2020-08-15
 */
@RestController
@RequestMapping(value = "person")
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping(value = "/add")
    public ResponseEntity<PersonDTO> addPerson(@Valid @RequestBody PersonDTO personDTO) {
        personDTO = personService.addPerson(personDTO);
        return new ResponseEntity<PersonDTO>(personDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<PersonDTO> updatePerson(@Valid @RequestBody PersonDTO personDTO) {
        personDTO = personService.updatePerson(personDTO);
        return new ResponseEntity<PersonDTO>(personDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{personId}")
    public ResponseEntity<PersonDTO> getPerson(@Valid @PathVariable("personId") Integer personId) {
        PersonDTO personDTO = personService.getPersonById(personId);
        return new ResponseEntity<PersonDTO>(personDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/get/all")
    public ResponseEntity<List<PersonDTO>> getAllPersons(@Valid @RequestParam("pageNumber") Integer pageNumber,
            @Valid @RequestParam("pageSize") Integer pageSize) {
        List<PersonDTO> personDTOs = personService.getAllPersons(pageNumber, pageSize);
        return new ResponseEntity<List<PersonDTO>>(personDTOs, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{personId}")
    public ResponseEntity<String> deletePerson(@Valid @PathVariable("personId") Integer personId) {
        boolean isDeleted = personService.deletePersonById(personId);
        String message = isDeleted ? "Person deleted successfully" : "Person has not been deleted successfully";
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

}

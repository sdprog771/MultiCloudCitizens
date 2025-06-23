package com.multicloud.citizens.controller;

import com.multicloud.citizens.model.Person;
import com.multicloud.citizens.repository.PersonRespository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citizens")
public class PersonController {

    private final PersonRespository personRespository;

    public PersonController(PersonRespository personRespository){
        this.personRespository = personRespository;
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    public List<Person> getAllPersons(@RequestParam(required = false) String at,
                                      @RequestParam(required = false) String firstName,
                                      @RequestParam(required = false) String lastName,
                                      @RequestParam(required = false) String gender,
                                      @RequestParam(required = false) LocalDateTime birthDate,
                                      @RequestParam(required = false) Long afm,
                                      @RequestParam(required = false) String homeAddress){

        boolean atIsEmpty = (at == null && at.isBlank());
        boolean firstNameIsEmpty = (firstName == null && firstName.isBlank());
        boolean lastNameIsEmpty = (lastName == null && lastName.isBlank());
        boolean genderIsEmpty = (gender == null && gender.isBlank());
        boolean birthDateIsEmpty = (birthDate == null);
        boolean afmIsEmpty = (afm == null && afm ==0);
        boolean homeAddressIsEmpty = (homeAddress == null && homeAddress.isBlank());

        if(!atIsEmpty || !firstNameIsEmpty || !lastNameIsEmpty || !genderIsEmpty || !birthDateIsEmpty || !afmIsEmpty || !homeAddressIsEmpty) {
            return personRespository.findByFields(at, firstName, lastName, gender, birthDate, afm, homeAddress);
        }
        else {
            return personRespository.findAll();
        }
    }


    @GetMapping(value = "{id}", produces = {"application/json", "application/xml"})
    public Person getPerson(@PathVariable String id){
        return personRespository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Citizen with given id does not exist!"));
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) throws UnknownHostException, URISyntaxException {
        if(personRespository.findById(person.getAt()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Citizen already exists!");
		else {
            personRespository.save(person);
        }
        try {
            String url = "http://" + InetAddress.getLocalHost().getHostName() + ":8080/api/citizens/" + person.getAt();
            return ResponseEntity.created(new URI(url)).build();
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    @PutMapping(value = "{id}", consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> updatePerson(@PathVariable String id, @Valid @RequestBody Person person){
        if (!person.getAt().equals(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trying to update citizen with wrong id!");
        else return personRespository.findById(id)
                .map(currentPerson -> {
                    currentPerson.setAt(person.getAt());
                    currentPerson.setFirstName(person.getFirstName());
                    currentPerson.setLastName(person.getLastName());
                    currentPerson.setGender(person.getGender());
                    currentPerson.setBirthDate(person.getBirthDate());
                    currentPerson.setAfm(person.getAfm());
                    currentPerson.setHomeAddress(person.getHomeAddress());
                    personRespository.save(currentPerson);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Citizen with given id does not exist!"));
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> deletePerson(@PathVariable String id) {
        return personRespository.findById(id)
                .map(currentPerson -> {
                    personRespository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Citizen with given id does not exist!"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            System.out.println("Fieldname is: " + fieldName + " ErrorMessage:" + errorMessage);
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

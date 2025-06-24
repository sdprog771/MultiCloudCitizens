package com.multicloud.citizens.unit;

import com.multicloud.citizens.model.Person;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.multicloud.citizens.repository.PersonRespository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class DBTest implements TestInterface{

    @Autowired
    PersonRespository personRespository;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");


    @ParameterizedTest
    @Order(5)
    @CsvFileSource(resources="/positivePerson.csv")
    void checkPositivePersonWithDB(ArgumentsAccessor argumentsAccessor) {
        Person person = PersonUtility.createPositivePerson(argumentsAccessor,0);
        personRespository.save(person);
        person = personRespository.findById(argumentsAccessor.getString(0)).orElse(null);
        assertNotNull(person);
        PersonUtility.checkPerson(person,argumentsAccessor);
    }

    @ParameterizedTest
    @Order(2)
    @CsvSource({
            "AT1, FirstName1, LastName1, Gender1, 01-01-2000",
            "AT2, FirstName2, LastName2, Gender2, 02-02-2000",
            "AT3, FirstName3, LastName3, Gender3, 03-03-2000",
    })
    void checkPersonDeletion(String at, String firstName, String lastName, String gender, String birthDate) {
        Date birthDateDate = null;
        try {
            birthDateDate = formatter.parse(birthDate);
        } catch (ParseException e){
            throw new IllegalArgumentException("birthDate is not parseable");
        }
        Person person = new Person.Builder(at, firstName, lastName, gender, birthDateDate).build();
        personRespository.save(person);
        personRespository.deleteById(at);
        person = personRespository.findById(at).orElse(null);
        assertNull(person);
    }

    @ParameterizedTest
    @Order(4)
    @CsvSource({
            "AT123456, FirstName1, LastName1, Gender1, 01-01-2000",
            "AT789123, FirstName2, LastName2, Gender2, 02-02-2000",
            "AT456789, FirstName3, LastName3, Gender3, 03-03-2000",
    })
    void checkPersonUpdate(String at, String firstName, String lastName, String gender, String birthDate) {
        Date birthDateDate = null;
        try {
            birthDateDate = formatter.parse(birthDate);
        } catch (ParseException e){
            throw new IllegalArgumentException("birthDate is not parseable");
        }
        Person person = new Person.Builder(at, firstName, lastName, gender, birthDateDate).build();
        personRespository.save(person);
        Random r = new Random();
        int choice = r.nextInt(3);
        switch(choice) {
            case 0: person.setAt("AT"); break;
            case 1: person.setFirstName("First Name"); break;
            case 2: person.setLastName("Last Name"); break;
        }
        personRespository.save(person);
        person = personRespository.findById(at).orElse(null);
        assertNotNull(person);
    }

    @Test
    @Order(3)
    void checkPersonRetrieval() {
        Date birthDate1 = null;
        Date birthDate2 = null;
        Date birthDate3 = null;

        try {
            birthDate1 = formatter.parse("01-01-2000");
            birthDate2 = formatter.parse("02-02-2000");
            birthDate3 = formatter.parse("03-03-2000");

        } catch (ParseException e) {
            throw new IllegalArgumentException("BirthDates are not parseable.");
        }

        Person person1 = new Person.Builder("AO123456", "FirstNameA", "LastNameA", "GenderA", birthDate1).build();
        Person person2 = new Person.Builder("AO123457", "FirstNameB", "LastNameB", "GenderB", birthDate2).build();
        Person person3 = new Person.Builder("AO123458", "FirstNameC", "LastNameC", "GenderC", birthDate3).build();
        personRespository.save(person1);
        personRespository.save(person2);
        personRespository.save(person3);
        List<Person> persons = personRespository.findAll();

        assertEquals(persons.size(),3);
        int matches = 0;
        for (Person person: persons) {
            if (person.equals(person1) || person.equals(person2) || person.equals(person3)) {
                matches++;
            }
        }
        assertEquals(matches,3);

        persons = personRespository.findByAt("AO123456");
        assertEquals(persons.size(),1);
        assertEquals(persons.get(0),person1);
        persons = personRespository.findByAt("AO123457");
        assertEquals(persons.size(),1);
        assertEquals(persons.get(0),person2);

    }
}

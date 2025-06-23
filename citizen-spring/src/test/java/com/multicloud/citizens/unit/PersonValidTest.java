package com.multicloud.citizens.unit;

import com.multicloud.citizens.model.Person;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PersonValidTest implements TestInterface{

        private static Validator validator = null;

        @BeforeAll
        static void constructValidator() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }

        private static List<String> getMessages(Set<ConstraintViolation<Person>> viols){
            List<String> messages = new ArrayList<String>();
            for (ConstraintViolation<Person> viol: viols) {
                messages.add(viol.getMessage());
            }
            return messages;
        }

        @ParameterizedTest
        @ValueSource(strings = { "AO1234567", "AO1234568", "AO AO AO", "123 45 6" })
        void checkInvalidAT(String at) {
            Person person = new Person();
            person.setAt(at);
            assertEquals(at,person.getAt());

            Set<ConstraintViolation<Person>> viols = validator.validate(person);
            assertNotEquals(viols.size(), 0);

            List<String> messages = getMessages(viols);
            assertTrue(messages.contains("AT is invalid!"));
        }

        @ParameterizedTest
        @ValueSource(strings = { "AO123456", "AB123456", "123RF234"})
        void checkValidAT(String at) {
            Person person = new Person();
            person.setAt(at);
            assertEquals(at,person.getAt());

            Set<ConstraintViolation<Person>> viols = validator.validate(person);
            assertNotEquals(viols.size(), 0);

            List<String> messages = getMessages(viols);
            assertFalse(messages.contains("AT is invalid!"));
        }

        @ParameterizedTest
        @ValueSource(strings = { "*First Name", "^First Name", "First Name&", "Name%2" })
        void checkInvalidFirstName(String firstName) {
            Person person = new Person();
            person.setFirstName(firstName);
            assertEquals(firstName,person.getFirstName());

            Set<ConstraintViolation<Person>> viols = validator.validate(person);
            assertNotEquals(viols.size(), 0);

            List<String> messages = getMessages(viols);
            assertTrue(messages.contains("First name is invalid!"));
        }

        @ParameterizedTest
        @ValueSource(strings = { "First Name", "Second Name", "First Long Name"})
        void checkValidFirstName(String firstName) {
            Person person = new Person();
            person.setFirstName(firstName);
            assertEquals(firstName,person.getFirstName());

            Set<ConstraintViolation<Person>> viols = validator.validate(person);
            assertNotEquals(viols.size(), 0);

            List<String> messages = getMessages(viols);
            assertFalse(messages.contains("First name is invalid!"));
        }

    @ParameterizedTest
    @ValueSource(strings = { "*Last Name", "^Last Name", "Last Name&", "Name%2" })
    void checkInvalidLastName(String lastName) {
        Person person = new Person();
        person.setLastName(lastName);
        assertEquals(lastName,person.getLastName());

        Set<ConstraintViolation<Person>> viols = validator.validate(person);
        assertNotEquals(viols.size(), 0);

        List<String> messages = getMessages(viols);
        assertTrue(messages.contains("Last name is invalid!"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "Last Name", "Another Name", "Last Long Name"})
    void checkValidLastName(String lastName) {
        Person person = new Person();
        person.setFirstName(lastName);
        assertEquals(lastName,person.getFirstName());

        Set<ConstraintViolation<Person>> viols = validator.validate(person);
        assertNotEquals(viols.size(), 0);

        List<String> messages = getMessages(viols);
        assertFalse(messages.contains("Last name is invalid!"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "*Gender", "^Gender", "Gender&", "Gender%2" })
    void checkInvalidGender(String gender) {
        Person person = new Person();
        person.setGender(gender);
        assertEquals(gender,person.getGender());

        Set<ConstraintViolation<Person>> viols = validator.validate(person);
        assertNotEquals(viols.size(), 0);

        List<String> messages = getMessages(viols);
        assertTrue(messages.contains("Gender is invalid!"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "GenderA", "GenderB", "Gender AB"})
    void checkValidGender(String gender) {
        Person person = new Person();
        person.setGender(gender);
        assertEquals(gender,person.getGender());

        Set<ConstraintViolation<Person>> viols = validator.validate(person);
        assertNotEquals(viols.size(), 0);

        List<String> messages = getMessages(viols);
        assertFalse(messages.contains("Gender is invalid!"));
    }


}

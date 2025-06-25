package com.multicloud.citizens.unit;

import com.multicloud.citizens.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest implements TestInterface {

    @ParameterizedTest
//    @CsvFileSource(resources="/resources/positivePerson.csv")
    @CsvFileSource(resources="/positivePerson.csv")
    void checkPositivePerson(ArgumentsAccessor argumentsAccessor){
        Person person = PersonUtility.createPositivePerson(argumentsAccessor,0);
        PersonUtility.checkPerson(person,argumentsAccessor);
    }

    @ParameterizedTest
    @CsvFileSource(resources="/negativePerson.csv")
    void checkNegativePerson(ArgumentsAccessor argumentsAccessor){
        Exception e = assertThrows(IllegalArgumentException.class, () -> PersonUtility.createNegativePerson(argumentsAccessor));
        assertEquals(argumentsAccessor.getString(7), e.getMessage());
    }

    @Test
    void checkEmptyConstructor(){
        Person person = new Person();
        assertNull(person.getAt());
        assertNull(person.getFirstName());
        assertNull(person.getLastName());
        assertNull(person.getGender());
        assertNull(person.getBirthDate());
        assertNull(person.getAfm());
        assertNull(person.getHomeAddress());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void nullEmptyAndBlankStringsforAT(String at) {
        Person person = new Person();
        Exception e = assertThrows(IllegalArgumentException.class, ()-> person.setAt(at));
        assertEquals("AT cannot be null or empty", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "AT1", "AT2", "I", "123" })
    void checkProperStringsforAT(String at) {
        Person person = new Person();
        person.setAt(at);
        assertEquals(at,person.getAt());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void nullEmptyAndBlankStringsforFirstName(String firstName) {
        Person person = new Person();
        Exception e = assertThrows(IllegalArgumentException.class, ()-> person.setFirstName(firstName));
        assertEquals("First name cannot be null or empty", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "FirstName1", "FirstName2", "F", "This is a first name" })
    void checkProperStringsforFirstName(String firstName) {
        Person person = new Person();
        person.setFirstName(firstName);
        assertEquals(firstName,person.getFirstName());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void nullEmptyAndBlankStringsforLastName(String lastName) {
        Person person = new Person();
        Exception e = assertThrows(IllegalArgumentException.class, ()-> person.setLastName(lastName));
        assertEquals("Last name cannot be null or empty", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "LastName1", "LastName2", "L", "This is a last name" })
    void checkProperStringsforLastName(String lastName) {
        Person person = new Person();
        person.setLastName(lastName);
        assertEquals(lastName,person.getLastName());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void checkNegativeValuesforGender(String gender) {
        Person person = new Person();
        Exception e = assertThrows(IllegalArgumentException.class, ()-> person.setGender(gender));
        assertEquals("Gender cannot be null or empty", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "Gender1",  "Gender2", "G"})
    void checkPositiveValuesforGender(String gender) {
        Person person = new Person();
        person.setGender(gender);
        assertEquals(person.getGender(),gender);
    }

//    @ParameterizedTest
////    @NullAndEmptySource
//    @ValueSource(strings = { " ", "   ", "\t", "\n" })
//    void checkNegativeValuesforBirthDate(String birthDate) {
//        Person person = new Person();
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        person.setBirthDate(LocalDate.parse(birthDate));
//        assertNull(person.getBirthDate());
//    }

    @ParameterizedTest
    @ValueSource(strings = { "10-01-2000", "20-02-2000" })
    void checkPositiveValuesforBirthDate(String birthDate) {
        Person person = new Person();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        person.setBirthDate(LocalDate.parse(birthDate, formatter));
        assertEquals(person.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),birthDate);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void checkNegativeValuesforAFM(String afm) {
        Person person = new Person();
        person.setAfm(Long.getLong(afm));
        assertNull(person.getAfm());
    }

    @ParameterizedTest
    @ValueSource(strings = { "123456789", "123" })
    void checkPositiveValuesforAFM(Long afm) {
        Person person = new Person();
        person.setAfm(afm);
        assertEquals(person.getAfm(),afm);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void checkNegativeValuesforHomeAddress(String homeAddress) {
        Person person = new Person();
        person.setHomeAddress(homeAddress);
        assertEquals(person.getHomeAddress(), homeAddress);
    }

    @ParameterizedTest
    @ValueSource(strings = { "Home Address 1", "Home Address 2", "Home" })
    void checkPositiveValuesforHomeAddress(String homeAddress) {
        Person person = new Person();
        person.setHomeAddress(homeAddress);
        assertEquals(person.getHomeAddress(),homeAddress);
    }

}

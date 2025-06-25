package com.multicloud.citizens.unit;

import com.multicloud.citizens.model.Person;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonUtility {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static Person createPositivePerson(ArgumentsAccessor argumentsAccessor, int start){
        LocalDate birthDateValue = null;

        String at = argumentsAccessor.getString(start+0);
        String firstName = argumentsAccessor.getString(start+1);
        String lastName = argumentsAccessor.getString(start+2);
        String gender = argumentsAccessor.getString(start+3);
        String birthdate = argumentsAccessor.getString(start+4);
        Long afm = argumentsAccessor.getLong(start+5);
        String homeAddress = argumentsAccessor.getString(start+6);

        try {
            birthDateValue = LocalDate.parse(birthdate, formatter);
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("The birthDate is not parseable.");
        }

        return new Person.Builder(at,firstName, lastName, gender, birthDateValue).afm(afm).homeAddress(homeAddress).build();
    }

    public static void createNegativePerson(ArgumentsAccessor argumentsAccessor){
        LocalDate birthDateValue = null;

        String at = argumentsAccessor.getString(0);
        String firstName = argumentsAccessor.getString(1);
        String lastName = argumentsAccessor.getString(2);
        String gender = argumentsAccessor.getString(3);
        String birthdate = argumentsAccessor.getString(4);
        Long afm = argumentsAccessor.getLong(5);
        String homeAddress = argumentsAccessor.getString(6);

        try {
            birthDateValue = LocalDate.parse(birthdate, formatter);
        }catch (DateTimeParseException | NullPointerException e ){
            throw new IllegalArgumentException("Birthdate cannot be null or empty");
        }

        Person person = new Person.Builder(at,firstName, lastName, gender, birthDateValue).afm(afm).homeAddress(homeAddress).build();

        System.out.println("Person: " + person);
    }

public static void checkPerson(Person person, ArgumentsAccessor argumentsAccessor){
        assertEquals(person.getAt(),argumentsAccessor.get(0));
        assertEquals(person.getFirstName(),argumentsAccessor.get(1));
        assertEquals(person.getLastName(),argumentsAccessor.get(2));
        assertEquals(person.getGender(),argumentsAccessor.get(3));
        assertEquals(person.getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),argumentsAccessor.get(4));
        assertEquals(person.getAfm() != null ? person.getAfm().toString() : null,argumentsAccessor.get(5));
        assertEquals(person.getHomeAddress(),argumentsAccessor.get(6));
}


}

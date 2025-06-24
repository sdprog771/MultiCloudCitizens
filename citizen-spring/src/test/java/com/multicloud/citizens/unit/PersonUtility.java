package com.multicloud.citizens.unit;

import com.multicloud.citizens.model.Person;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonUtility {

    private final static SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);
    public static Person createPositivePerson(ArgumentsAccessor argumentsAccessor, int start){
        Date birthDateValue = null;

        String at = argumentsAccessor.getString(start+0);
        String firstName = argumentsAccessor.getString(start+1);
        String lastName = argumentsAccessor.getString(start+2);
        String gender = argumentsAccessor.getString(start+3);
        String birthdate = argumentsAccessor.getString(start+4);
        Long afm = argumentsAccessor.getLong(start+5);
        String homeAddress = argumentsAccessor.getString(start+6);

        try {
            birthDateValue = formatter.parse(birthdate);
        }catch (ParseException e){
            throw new IllegalArgumentException("The birthDate is not parseable.");
        }

        return new Person.Builder(at,firstName, lastName, gender, birthDateValue).afm(afm).homeAddress(homeAddress).build();
    }

    public static void createNegativePerson(ArgumentsAccessor argumentsAccessor){
        Date birthDateValue = null;

        String at = argumentsAccessor.getString(0);
        String firstName = argumentsAccessor.getString(1);
        String lastName = argumentsAccessor.getString(2);
        String gender = argumentsAccessor.getString(3);
        String birthdate = argumentsAccessor.getString(4);
        Long afm = argumentsAccessor.getLong(5);
        String homeAddress = argumentsAccessor.getString(6);

        try {
            birthDateValue = formatter.parse(birthdate);
        }catch (ParseException e){
            throw new IllegalArgumentException("The birthDate is not parseable.");
        }

        Person person = new Person.Builder(at,firstName, lastName, gender, birthDateValue).afm(afm).homeAddress(homeAddress).build();

        System.out.println("Person: " + person);
    }

public static void checkPerson(Person person, ArgumentsAccessor argumentsAccessor){
        assertEquals(person.getAt(),argumentsAccessor.get(0));
        assertEquals(person.getFirstName(),argumentsAccessor.get(1));
        assertEquals(person.getLastName(),argumentsAccessor.get(2));
        assertEquals(person.getGender(),argumentsAccessor.get(3));
        assertEquals(person.getBirthDate(),argumentsAccessor.get(4));
        assertEquals(person.getAfm(),argumentsAccessor.get(5));
        assertEquals(person.getHomeAddress(),argumentsAccessor.get(6));
}


}

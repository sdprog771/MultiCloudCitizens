package com.multicloud.citizens.repository;

import com.multicloud.citizens.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PersonRespository extends JpaRepository<Person, String> {
    List<Person> findByAtOrFirstNameOrLastNameOrGenderOrBirthDateOrAfmOrHomeAddress(String at, String firstName, String lastName, String gender, Date birthDate, Long afm, String homeAddress);
    List<Person> findByAt(String at);
}

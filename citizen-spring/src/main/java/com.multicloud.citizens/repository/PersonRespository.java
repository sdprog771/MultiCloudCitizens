package com.multicloud.citizens.repository;

import com.multicloud.citizens.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRespository extends JpaRepository<Person, String> {
    List<Person> findByFields(Long at, String firstName, String lastName, String gender, LocalDateTime birthDate, Long afm, String homeAddress);
}

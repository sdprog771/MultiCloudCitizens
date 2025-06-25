package com.multicloud.citizens.integration;

import com.multicloud.citizens.model.Person;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DisplayName("Adding Person Testing")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("classpath:application-test.properties")
public class SecondIT implements TestLifecycleLogger {
    @Autowired
    private WebApplicationContext webApplicationContext;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @BeforeAll
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    @Order(1)
    public void addWrongPerson() throws Exception{
        Person person = new Person();
        given().contentType("application/json").body(person).when().post("/api/citizens").then().assertThat().statusCode(400);
    }

    public static Person createPerson(String at) {
        Random r = new Random();
        LocalDate birthDateValue = null;
        Person person = new Person();
        person.setAt(at);

        int firstNameId = r.nextInt(10) + 1;
        person.setFirstName("FirstName" + firstNameId);
        int lastNameId = r.nextInt(10) + 1;
        person.setLastName("LastName" + lastNameId);
        int genderId = r.nextInt(10) + 1;
        person.setGender("FirstName" + genderId);
        try{
            birthDateValue = LocalDate.parse("20-10-2000", formatter);
        }catch(DateTimeParseException e){
            throw new IllegalArgumentException("The birthDate is not parseable.");
        }
        person.setBirthDate(birthDateValue);

        return person;
    }

    @Test
    @Order(2)
    public void postCorrectPerson() throws Exception{
        Person person = createPerson("AO123456");
        given().contentType("application/json").body(person).when().post("/api/citizens").then().assertThat().statusCode(201);
    }

    @Test
    @Order(3)
    public void getExistingPerson() throws Exception{
        given().accept("application/json").get("/api/citizens/AO123456").then().
                assertThat().statusCode(200).and().body("at", equalTo("AO123456"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "AO123457", "AO123458", "AO123459" })
    @Order(4)
    void addCorrectPerson(String at) {
        Person person = createPerson(at);
        given().contentType("application/json").body(person).when().post("/api/citizens").then().assertThat().statusCode(201);
    }

    @Test
    @Order(5)
    public void getExistingPersons() throws Exception{
        List<Person> persons = given().accept("application/json").get("/api/citizens").then().assertThat().statusCode(200).
                extract().as(new TypeRef<List<Person>>(){});
        assertThat(persons, hasSize(4));
        assertThat(persons.get(0).getAt(),anyOf(equalTo("AO123456"),equalTo("AO123457"),equalTo("AO123458"),equalTo("AO123459")));
        assertThat(persons.get(1).getAt(),anyOf(equalTo("AO123456"),equalTo("AO123457"),equalTo("AO123458"),equalTo("AO123459")));
        assertThat(persons.get(2).getAt(),anyOf(equalTo("AO123456"),equalTo("AO123457"),equalTo("AO123458"),equalTo("AO123459")));
        assertThat(persons.get(3).getAt(),anyOf(equalTo("AO123456"),equalTo("AO123457"),equalTo("AO123458"),equalTo("AO123459")));
    }

}

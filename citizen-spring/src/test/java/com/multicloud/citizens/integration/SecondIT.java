package com.multicloud.citizens.integration;

import com.multicloud.citizens.model.Person;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DisplayName("Adding Person Testing")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecondIT implements TestLifecycleLogger {
    @Autowired
    private WebApplicationContext webApplicationContext;

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

    private Person createPerson(String at) {
        Random r = new Random();
        Person person = new Person();
        person.setAt(at);

        int firstNameId = r.nextInt(10) + 1;
        person.setFirstName("FirstName" + firstNameId);
        int lastNameId = r.nextInt(10) + 1;
        person.setLastName("LastName" + lastNameId);
        int genderId = r.nextInt(10) + 1;
        person.setGender("FirstName" + genderId);
        person.setBirthDate(LocalDateTime.parse("20-10-2000"));

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
    @ValueSource(strings = { "AO123456", "AO123457", "AO123458" })
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

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

import java.util.List;

import static com.multicloud.citizens.integration.SecondIT.createPerson;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(3)
@DisplayName("Deleting Person Testing")
@TestPropertySource("classpath:application-test.properties")
public class ThirdIT implements TestLifecycleLogger {
    @Autowired
    private WebApplicationContext webApplicationContext;

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
    public void deleteWrongPerson() throws Exception{
        given().delete("/api/citizens/AO123451").then().assertThat().statusCode(404);
    }

    @Test
    @Order(2)
    public void deleteCorrectPerson() throws Exception{
        given().delete("/api/citizens/AO123456").then().assertThat().statusCode(204);
    }

    @Test
    @Order(3)
    public void getNonExistingPerson() throws Exception{
        given().accept("application/json").get("/api/citizens/AO123452").then().assertThat().statusCode(404);
    }

    @ParameterizedTest
    @ValueSource(strings = { "AO123455", "AO123456" })
    @Order(4)
    void addCorrectPerson(String at) {
        Person person = createPerson(at);
        RestAssured.given().contentType("application/json").body(person).when().post("/api/citizens").then().assertThat().statusCode(201);
    }

    @Test
    @Order(5)
    public void getExistingPersons() throws Exception{
        List<Person> persons = RestAssured.given().accept("application/json").get("/api/citizens").then().assertThat().statusCode(200).
                extract().as(new TypeRef<List<Person>>(){});
        assertThat(persons, hasSize(5));
        assertThat(persons.get(0).getAt(),anyOf(equalTo("AO123455"),equalTo("AO123456"),equalTo("AO123457"),equalTo("AO123458"),equalTo("AO123459")));
        assertThat(persons.get(1).getAt(),anyOf(equalTo("AO123455"),equalTo("AO123456"),equalTo("AO123457"),equalTo("AO123458"),equalTo("AO123459")));
        assertThat(persons.get(2).getAt(),anyOf(equalTo("AO123455"),equalTo("AO123456"),equalTo("AO123457"),equalTo("AO123458"),equalTo("AO123459")));
        assertThat(persons.get(3).getAt(),anyOf(equalTo("AO123455"),equalTo("AO123456"),equalTo("AO123457"),equalTo("AO123458"),equalTo("AO123459")));
        assertThat(persons.get(4).getAt(),anyOf(equalTo("AO123455"),equalTo("AO123456"),equalTo("AO123457"),equalTo("AO123458"),equalTo("AO123459")));
    }
}

package com.multicloud.citizens.integration;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("RestPerson API Testing")
@TestPropertySource("classpath:application-test.properties")
public class FirstIT implements TestLifecycleLogger {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    public void initialiseRestAssuredMockMvcWebApplicationContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    @Order(1)
    public void callPersons() throws Exception
    {
        given().accept("application/json").get("/api/citizens").then().assertThat().contentType("application/json").and().statusCode(200);
    }

    @Test
    @Order(2)
    public void getPersonWhileNoOneExists() throws Exception{
        given().accept("application/json").get("/api/citizens/AO123456").then().assertThat().statusCode(404);
    }
}

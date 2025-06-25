package com.multicloud.citizen_client;
import com.multicloud.citizen_client.client.MyRestClient;
import com.multicloud.citizen_client.configuration.ImmutableApiConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;

@SpringBootApplication
@EnableConfigurationProperties(ImmutableApiConfiguration.class)
public class PersonClientSpringApplication implements CommandLineRunner{

    @Autowired
    private MyRestClient mrc;

    public static void main(String[] args) {
        SpringApplication.run(PersonClientSpringApplication.class, args);
    }

    @Override
    public void run(String... args) {
        MediaType xml = MediaType.APPLICATION_XML;
        MediaType json = MediaType.APPLICATION_JSON;
        String at1 = "AO123456";
        String at2 = "AB123456";
        //Adding two citizens
        mrc.addPerson(at1,json);
        mrc.addPerson(at2,json);
        mrc.addPerson(at1,json);

        //Getting all citizens in different media types
        mrc.getPersons(xml);
        mrc.getPersons(json);

        //Getting filtered citizens in different media types
        mrc.getPersonsWithParams("AO123456",null,null,null,null,xml);
        mrc.getPersonsWithParams(null,"FirstName",null,null,null,json);

        //Getting one person in different media types
        mrc.getPerson(at1,xml);
        mrc.getPerson(at1,json);

        //Updating one person & checking the update
        mrc.updatePerson(at1,json);
        mrc.getPerson(at1,json);

        //Deleting first person & checking the deletion
        mrc.deletePerson(at1);
        mrc.deletePerson(at1);
        mrc.getPersons(json);
    }

}

package citizen_client.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.multicloud.citizens.Person;
import citizen_client.configuration.ImmutableApiConfiguration;

@Component
public class MyRestClient {
    private final ImmutableApiConfiguration details;

    private RestClient client;

    private static int counter = 1;

    public MyRestClient(ImmutableApiConfiguration details){
        this.details = details;
        initClient();
    }

    private void initClient() {
        String url = "http://" + details.getHost() + ":" + details.getPort() + "/" + details.getApi();
        client = RestClient.create(url);
    }

    public void getPersons(MediaType type) {
        client.get()
                .accept(type)
                .exchange(
                        (request, response) -> {
                            if (response.getStatusCode().is4xxClientError())
                                System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
                                        " and message: " + response.bodyTo(String.class));
                            else if (response.getStatusCode().is5xxServerError())
                                System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
                                        " and message: " + response.bodyTo(String.class));
                            else if (response.getStatusCode().is2xxSuccessful()) {
                                System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
                                System.out.println("The list of citizens is: " + response.bodyTo(String.class));
                            }
                            return null;
                        }
                );
    }

    public void getPersonsWithParams(String at, String firstName, String lastName, String gender, LocalDateTime birthdate, MediaType type) {
        String queryPart = "";
        if (at != null && !at.isBlank()) queryPart += "at=" + at;
        if (firstName != null && !firstName.isBlank()) queryPart += "firstName=" + firstName;
        if (lastName != null && !lastName.isBlank()) queryPart += "lastName=" + lastName;
        if (gender != null && !gender.isBlank()) queryPart += "gender=" + gender;
        if (birthdate != null) queryPart += "birthdate=" + birthdate;
        if (!queryPart.isBlank()) queryPart = "?" + queryPart;
        client.get()
                .uri(queryPart)
                .accept(type)
                .exchange(
                        (request, response) -> {
                            if (response.getStatusCode().is4xxClientError())
                                System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
                                        " and message: " + response.bodyTo(String.class));
                            else if (response.getStatusCode().is5xxServerError())
                                System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
                                        " and message: " + response.bodyTo(String.class));
                            else if (response.getStatusCode().is2xxSuccessful()) {
                                System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
                                System.out.println("The list of citizens is: " + response.bodyTo(String.class));
                            }
                            return null;
                        }
                );
    }

    public void getPerson(String at, MediaType type) {
        client.get()
                .uri("/{id}",at)
                .accept(type)
                .exchange(
                        (request, response) -> {
                            if (response.getStatusCode().is4xxClientError())
                                System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
                                        " and message: " + response.bodyTo(String.class));
                            else if (response.getStatusCode().is5xxServerError())
                                System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
                                        " and message: " + response.bodyTo(String.class));
                            else if (response.getStatusCode().is2xxSuccessful()) {
                                System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
                                System.out.println("The citizen with AT: " + at + " is: " + response.bodyTo(String.class));
                            }
                            return null;
                        }
                );
    }

    private Person createPerson(String at) {
        Person person = new Person();
        person.setAt(at);
        person.setFirstName("First name" + counter);
        person.setLastName("Last name" + counter);
        person.setBirthDate("Birth date" + counter);
        person.setAfm("AFM" + counter);
        person.setHomeAddress("Home address" + counter);
        counter++;

        return person;
    }

    public void addPerson(String at, MediaType type) {
        Person person = createPerson(at);
        client.post()
                .contentType(type)
                .body(person)
                .exchange(
                        (request, response) -> {
                            if (response.getStatusCode().is4xxClientError()) {
                                System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
                                        " and message: " + response.bodyTo(String.class));
                            }
                            else if (response.getStatusCode().is5xxServerError()) {
                                System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
                                        " and message: " + response.bodyTo(String.class));
                            }
                            else if (response.getStatusCode().is2xxSuccessful()) {
                                System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
                                System.out.println("The citizen with AT: " + at + " was created successfully");
                                System.out.println("The created citizen's URL is: " + response.getHeaders().get("Location"));
                            }
                            return null;
                        }
                );
    }

    public void updatePerson(String at, MediaType type) {
        Person person = createPerson(at);
        person.setFirstName("FirstName");
        client.put()
                .uri("/{id}",at)
                .contentType(type)
                .body(person)
                .exchange(
                        (request, response) -> {
                            if (response.getStatusCode().is4xxClientError()) {
                                System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
                                        " and message: " + response.bodyTo(String.class));
                            }
                            else if (response.getStatusCode().is5xxServerError()) {
                                System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
                                        " and message: " + response.bodyTo(String.class));
                            }
                            else if (response.getStatusCode().is2xxSuccessful()) {
                                System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
                                System.out.println("The citizen with AT: " + at + " has been successfully updated");
                            }
                            return null;
                        }
                );
    }

    public void deletePerson(String at) {
        client.delete()
                .uri("/{id}",at)
                .exchange(
                        (request, response) -> {
                            if (response.getStatusCode().is4xxClientError()) {
                                System.out.println("Client error with HTTP Status Code: " + response.getStatusCode().value() +
                                        " and message: " + response.bodyTo(String.class));
                            }
                            else if (response.getStatusCode().is5xxServerError()) {
                                System.out.println("Server error with HTTP Status Code: " + response.getStatusCode().value() +
                                        " and message: " + response.bodyTo(String.class));
                            }
                            else if (response.getStatusCode().is2xxSuccessful()) {
                                System.out.println("The HTTP Status Code is: " + response.getStatusCode().value());
                                System.out.println("The citizen with AT: " + at + " has been successfully deleted");
                            }
                            return null;
                        }
                );
    }
}

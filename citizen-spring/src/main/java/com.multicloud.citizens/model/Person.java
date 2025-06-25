package com.multicloud.citizens.model;

import com.multicloud.citizens.validator.NineDigitLong;
import com.multicloud.citizens.validator.ValidDateFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name="person")
public class Person {

    @Id
    @NotBlank(message = "AT number is mandatory.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8}$", message="AT is invalid!")//message="Insert up to eight characters, letters or numbers.")
    @Column(name="at_")
    private String at;

    @Column(name="firstName_")
    @NotBlank(message = "First name is mandatory.")
    @Basic(optional = false)
    private String firstName;

    @Column(name="lastName_")
    @NotBlank(message = "Last name is mandatory.")
    @Basic(optional = false)
    private String lastName;

    @Column(name="gender_")
    @NotBlank(message = "Gender is mandatory.")
    @Basic(optional = false)
    private String gender;

    @Column(name="birthDate_")
    @NotNull(message = "Date is mandatory.")
    @ValidDateFormat
    @Basic(optional = false)
    private LocalDate birthDate;

    @Column(name="afm_")
    @NineDigitLong
    private Long afm;

    @Column(name="homeAddress_")
    private String homeAddress;

    public Person (){}

    private Person (Builder builder){
        this.at = builder.at;
        this.firstName = builder.firstName;;
        this.lastName = builder.lastName;
        this.gender = builder.gender;
        this.birthDate = builder.birthDate;
        this.afm = builder.afm;
        this.homeAddress = builder.homeAddress;
    }

    public static class Builder{
        private String at = null;
        private String firstName = null;
        private String lastName = null;
        private String gender = null;
        private LocalDate birthDate = null;
        private Long afm = null;
        private String homeAddress = null;

        private static void checkSingleValue(String value, String message) throws IllegalArgumentException{
            if (value == null || value.trim().equals("")) throw new IllegalArgumentException(message + " cannot be null or empty");
        }
        private static void checkSingleValue(LocalDate value, String message){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            if (value == null) {
                throw new IllegalArgumentException(message + " cannot be null or empty");
            }
            String formattedDateString = null;
            try {
                formattedDateString = formatter.format(value);
            } catch (DateTimeException e) {
                throw new IllegalArgumentException(message + " cannot be null or empty");
            }
            if (formattedDateString.trim().equals("")) throw new IllegalArgumentException(message + " cannot be null or empty");
        }

        public Builder(String at, String firstName, String lastName, String gender, LocalDate birthDate) throws IllegalArgumentException{
            checkSingleValue(at, "AT");
            checkSingleValue(firstName, "First name");
            checkSingleValue(lastName, "Last name");
            checkSingleValue(gender, "Gender");
            checkSingleValue(birthDate, "Birthdate");

            this.at = at;
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.birthDate = birthDate;
        }

        public Builder afm(Long value){
            this.afm = value;
            return this;
        }

        public Builder homeAddress(String value){
            this.homeAddress = value;
            return this;
        }

        public Person build(){
            return new Person(this);
        }
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        Builder.checkSingleValue(at, "AT");
        this.at = at;
    }

    public Long getAfm() {
        return afm;
    }

    public void setAfm(Long afm) {
        this.afm = afm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        Builder.checkSingleValue(firstName, "First name");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        Builder.checkSingleValue(lastName, "Last name");
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        Builder.checkSingleValue(gender, "Gender");
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        Builder.checkSingleValue(birthDate, "Birthdate");
        this.birthDate = birthDate;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Override
    public String toString() {
        return "Person{" +
                "at='" + at + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", afm=" + afm +
                ", homeAddress='" + homeAddress + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!at.equals(person.at)) return false;
        if (!firstName.equals(person.firstName)) return false;
        if (!lastName.equals(person.lastName)) return false;
        if (!gender.equals(person.gender)) return false;
        if (!birthDate.equals(person.birthDate)) return false;
        if (!Objects.equals(afm, person.afm)) return false;
        return Objects.equals(homeAddress, person.homeAddress);
    }

    @Override
    public int hashCode() {
        int result = at.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + gender.hashCode();
        result = 31 * result + birthDate.hashCode();
        result = 31 * result + (afm != null ? afm.hashCode() : 0);
        result = 31 * result + (homeAddress != null ? homeAddress.hashCode() : 0);
        return result;
    }
}


package com.multicloud.citizens.dto;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

public class PersonDTO {

    @Id
    @NotBlank(message = "AT number is mandatory.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8}$", message="Insert up to eight characters, letters or numbers.")
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
    @NotBlank(message = "Date is mandatory.")
    @Pattern( regexp = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19|20)\\\\d{2}", message="Insert a valid date,[DD-MM-YYYY].")
    @Basic(optional = false)
    private String birthDate;

    @Column(name="afm_")
    @Pattern( regexp= "^\\d{9}$", message ="Insert a valid number, [0-9] up to nine numbers.")
    private String afm;

    @Column(name="homeAddress_")
    private String homeAddress;

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }
}

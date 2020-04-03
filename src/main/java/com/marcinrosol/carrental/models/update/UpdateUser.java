package com.marcinrosol.carrental.models.update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateUser {
    @NotNull
    @NotBlank
    @Size(max = 30)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String lastName;

    @NotNull
    @NotBlank
    @Email
    private String name; //name as an email, it'll be needed to spring security and to fetching user information from db

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UpdateUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

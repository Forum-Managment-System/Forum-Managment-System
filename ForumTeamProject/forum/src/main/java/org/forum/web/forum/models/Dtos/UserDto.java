package org.forum.web.forum.models.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDto {

    private String firstName;
    private String lastName;
    private String username;
    private String email;

    public UserDto() {
    }

    @NotNull(message = "First name can't be an empty field.")
    @Size(min = 4, max = 32, message = "First name should be between 4 and 32 symbols.")
    public String getFirstName() {
        return firstName;
    }

    @NotNull(message = "Last name can't be an empty field.")
    @Size(min = 4, max = 32, message = "Last name should be between 4 and 32 symbols.")
    public String getLastName() {
        return lastName;
    }

    @NotNull(message = "Username can't be an empty field.")
    @Size(min = 4, max = 32, message = "Username should be between 4 and 32 symbols.")
    public String getUsername() {
        return username;
    }

    @NotNull(message = "E-mail can't be an empty field.")
    @Email(message = "E-mail should be valid.")
    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
package org.forum.web.forum.models.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.forum.web.forum.models.PhoneNumber;

public class UserDto {
    @Size(min = 4, max = 32, message = "First name should be between 4 and 32 symbols.")
    private String firstName;
    @Size(min = 4, max = 32, message = "Last name should be between 4 and 32 symbols.")
    private String lastName;
    @Size(min = 4, max = 32, message = "Username should be between 4 and 32 symbols.")
    private String username;
    @Size(min = 8, max = 32, message = "Password should be at least 8 symbols.")
    private String password;
    @Email(message = "E-mail should be valid.")
    private String email;
//    @Size(min = 4, max = 15, message = "Phone number cannot exceed 15 symbols.")
    private PhoneNumber phoneNumber;
    private boolean banStatus;
    private boolean adminStatus;
    private String photoUrl;

    public UserDto() {
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public boolean getBanStatus() {
        return banStatus;
    }

    public boolean getAdminStatus() {
        return adminStatus;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBanStatus(boolean banStatus) {
        this.banStatus = banStatus;
    }

    public void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

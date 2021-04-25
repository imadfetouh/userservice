package com.imadelfetouh.userservice.model.dto;

import java.io.Serializable;

public class RegisterDTO implements Serializable {

    private String username;
    private String password;
    private String repeatPassword;
    private String photo;
    private String bio;
    private String location;
    private String website;

    public RegisterDTO(String username, String password, String repeatPassword, String photo, String bio, String location, String website) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.photo = photo;
        this.bio = bio;
        this.location = location;
        this.website = website;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }
}

package com.example.instagramclone.Model;

public class User {
    private String id;
    private String username;
    private String email;
    private String imageurl;
    private String gender;

    public User() {
    }

    public User(String id, String username, String email, String imageurl, String gender) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.imageurl = imageurl;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

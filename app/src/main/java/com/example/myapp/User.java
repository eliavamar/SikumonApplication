package com.example.myapp;

public class User {
    Email email;
    Password pass;
    String orientation;

    public User(String email, String pass, String orientation) {
        this.email.setEmail(email);
        this.pass.setPass(pass);
        this.orientation = orientation;
    }

    public String getEmail() {
        return email.getEmail();
    }

    public void setEmail(String email) {
        this.email.setEmail(email);
    }

    public String getPass() {
        return pass.getPass();
    }

    public void setPass(String pass) {
        this.pass.setPass(pass);
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
}

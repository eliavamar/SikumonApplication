package com.example.myapp;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class User {
    Email email;
    Password pass;
    String orientation;
    String department;
    public User(Email email, String pass, String orientation, String department) {
        this.email=email;
        this.pass =new Password(pass);
        this.orientation = orientation;
        this.department=department;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Email getEmail() {
        return email;
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

package com.example.myapp;

public class Password {
    String pass;
    static String specialChars=" !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    public Password(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    private static boolean containSpecialChars(String password){
        int count=0;
        boolean flagSmall=false;
        boolean flagBig=false;
        for (char c: specialChars.toCharArray()) {
            if(password.contains(c+"")) count++;
        }
        for (char c : password.toCharArray()) {
            if(c >= 'a' && c<='z') flagSmall=true;
            if (c>='A' && c<='Z') flagBig=true;
        }
        return count > 0 && flagBig && flagSmall;
    }

    public static boolean isValid(String password){
        if(password.length() < 8 || password.length() > 20 || !containSpecialChars(password)) return false;
        return true;
    }
}
package com.example.myapp;

public class PDFFile {
    String path;
    String URL;
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PDFFile(String path, String URL) {
        this.path = path;
        this.URL = URL;
        user=null;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}

package itu.nicolas.ticketing.models;

import util.MyFile;

public class User {
    String username;
    String password;
    String role;
    MyFile cin;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public MyFile getCin() {
        return cin;
    }

    public void setCin(MyFile cin) {
        this.cin = cin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.setRole(role);
    }
}

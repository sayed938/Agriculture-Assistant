package com.example.agriclutureassistant.ui.pojo;

public class UserSignUpData {

    private String username,phone,email,password;

    public UserSignUpData(String username, String phone, String email,String password) {
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

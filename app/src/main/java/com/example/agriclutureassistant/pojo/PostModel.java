package com.example.agriclutureassistant.pojo;

public class PostModel {

    String post_id;
    String user_name;
    String text;
    String Password;


    public PostModel(String post_id, String user_name, String text, String password) {
        this.post_id = post_id;
        this.user_name = user_name;
        this.text = text;
        Password = password;
    }

    public PostModel(String user_name, String text, String password) {
        this.user_name = user_name;
        this.text = text;
        Password = password;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}


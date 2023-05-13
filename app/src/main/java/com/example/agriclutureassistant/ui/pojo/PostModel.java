package com.example.agriclutureassistant.ui.pojo;

public class PostModel {

    String id;
    String user_name;
    String text;
    String postDate;
    String Password;


    public PostModel(String post_id, String user_name, String text, String postDate) {
        this.id = post_id;
        this.user_name = user_name;
        this.text = text;
        this.postDate = postDate;
    }

    public PostModel(String user_name, String text, String password) {
        this.user_name = user_name;
        this.text = text;
        this.Password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
}


package com.example.agriclutureassistant.pojo;

public class CommentModel {

    String id;
    String user_name;
    String text;
    String post_id;
    String CommintDate;
    String Password;

    public CommentModel(String id, String user_name, String text, String post_id, String commintDate, String password) {
        this.id = id;
        this.user_name = user_name;
        this.text = text;
        this.post_id = post_id;
        CommintDate = commintDate;
        Password = password;
    }

    public CommentModel(String post_id, String password) {
        this.post_id = post_id;
        Password = password;
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

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getCommintDate() {
        return CommintDate;
    }

    public void setCommintDate(String commintDate) {
        CommintDate = commintDate;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

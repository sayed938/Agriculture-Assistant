package com.example.agriclutureassistant.pojo;

public class PostsModel {
    String name,post;

    public PostsModel(String name, String post) {
        this.name = name;
        this.post = post;
    }

    public String getName() {
        return name;
    }

    public String getPost() {
        return post;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPost(String post) {
        this.post = post;
    }
}

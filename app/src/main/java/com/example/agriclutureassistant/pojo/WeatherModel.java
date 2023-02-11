package com.example.agriclutureassistant.pojo;

public class WeatherModel {
    String degree,clock;

    public WeatherModel(String degree, String clock) {
        this.degree = degree;
        this.clock = clock;
    }

    public String getDegree() {
        return degree;
    }

    public String getClock() {
        return clock;
    }
}

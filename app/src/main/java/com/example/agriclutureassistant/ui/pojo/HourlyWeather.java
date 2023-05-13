package com.example.agriclutureassistant.ui.pojo;

public class HourlyWeather {
    String degree,clock;

    public HourlyWeather(String degree, String clock) {
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

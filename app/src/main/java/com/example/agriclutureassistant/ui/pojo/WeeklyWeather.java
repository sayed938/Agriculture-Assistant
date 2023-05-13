package com.example.agriclutureassistant.ui.pojo;

public class WeeklyWeather {
    String day,temper;

    public WeeklyWeather(String day, String temper) {
        this.day = day;
        this.temper = temper;
    }

    public String getDay() {
        return day;
    }

    public String getTemper() {
        return temper;
    }
}

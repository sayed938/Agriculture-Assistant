package com.example.agriclutureassistant.pojo;

public class NextForecastModel {
    String day,temper;

    public NextForecastModel(String day, String temper) {
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

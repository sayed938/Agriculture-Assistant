package com.example.agriclutureassistant.data;

import static com.example.agriclutureassistant.ProjectData.BaseUrlTemper;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherBuilder {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseUrlTemper)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public GetTemper temperOBJ() {
        return retrofit.create(GetTemper.class);
    }
}

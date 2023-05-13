package com.example.agriclutureassistant.data;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRequestPlants {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://my-api.plantnet.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public SocialApiService apiService() {
        SocialApiService apiService = retrofit.create(SocialApiService.class);
        return apiService;
    }
}

package com.example.agriclutureassistant.data;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRequestPlants {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://my-api.plantnet.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Retrofit retrofit1 = new Retrofit.Builder()
            .baseUrl("https://modelapi-production-469b.up.railway.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public SocialApiService apiService() {
        SocialApiService apiService = retrofit.create(SocialApiService.class);
        return apiService;
    }
    public SocialApiService apiService1() {
        SocialApiService apiService1 = retrofit1.create(SocialApiService.class);
        return apiService1;
    }
}

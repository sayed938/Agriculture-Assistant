package com.example.agriclutureassistant.data;

import com.example.agriclutureassistant.pojo.WeatherModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetTemper {
    @GET("/v1/forecast.json")
    Observable<WeatherModel.Root> getTemper(@Query("key")String key, @Query("q")String q,@Query("days") int days);
}

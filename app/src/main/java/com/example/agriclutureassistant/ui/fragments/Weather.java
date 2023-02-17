package com.example.agriclutureassistant.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.pojo.WeatherModel;
import com.example.agriclutureassistant.ui.ViewModel;
import com.example.agriclutureassistant.ui.adapters.HourlyAdapter;
import com.example.agriclutureassistant.ui.adapters.WeeklyAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;

public class Weather extends Fragment {
    private ProgressBar bar;
    private ImageView weatherImg;
    private ViewModel viewModel;
    private TextView currentTemper, maxTemper, minTemper, windSpeed, humidity, rain, dateHourly;
    private RecyclerView recycler_weekly, recycler_hourly;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCurrentDetails(view);
    }
    private void getCurrentDetails(View v) {
        definingViews(v);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getCurrentTemper();
        viewModel.livedataWeather1.observe(getActivity(), new Observer<WeatherModel.Root>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(WeatherModel.Root root) {
                currentTemper.setText((int) (Math.round(root.current.temp_c)) + "º" + "C");
                Picasso.get().load("https:" + root.current.condition.icon).into(weatherImg);
                maxTemper.setText(Math.round(root.forecast.forecastday.get(0).day.maxtemp_c) + "º" + "C");
                minTemper.setText(Math.round(root.forecast.forecastday.get(0).day.mintemp_c) + "º" + "C");
                windSpeed.setText(Math.round(root.current.wind_kph) + " km/h");
                humidity.setText(root.current.humidity + "%");
                rain.setText(Math.round(root.forecast.forecastday.get(0).day.daily_chance_of_rain) + "%");
                dateHourly.setText(HomeFeatures.localDate());
            }
        });
        viewModel.livedataWeather2.observe(getActivity(), new Observer<List<WeatherModel.Hour>>() {
            @Override
            public void onChanged(List<WeatherModel.Hour> hours) {
                recycler_hourly.setAdapter(new HourlyAdapter(hours));
            }
        });
        viewModel.liveDataWeather3.observe(getActivity(), new Observer<List<WeatherModel.Forecastday>>() {
            @Override
            public void onChanged(List<WeatherModel.Forecastday> forecastdays) {
                recycler_weekly.setAdapter(new WeeklyAdapter(forecastdays));
                bar.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void definingViews(View v) {
        bar = v.findViewById(R.id.progress_bar);
        currentTemper = v.findViewById(R.id.current_temper);
        weatherImg = v.findViewById(R.id.weather_img);
        maxTemper = v.findViewById(R.id.max_temper);
        minTemper = v.findViewById(R.id.min_temper);
        windSpeed = v.findViewById(R.id.wind_speed);
        humidity = v.findViewById(R.id.weather_humidity);
        rain = v.findViewById(R.id.weather_rain);
        dateHourly = v.findViewById(R.id.date_hourly);
        recycler_weekly = v.findViewById(R.id.recyclerweekly);
        recycler_hourly = v.findViewById(R.id.recyclerhourly);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_hourly.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManagerWeek
                = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_weekly.setLayoutManager(layoutManagerWeek);
    }

}
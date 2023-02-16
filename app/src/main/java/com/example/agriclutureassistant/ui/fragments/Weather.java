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
import com.example.agriclutureassistant.pojo.WeeklyWeather;
import com.example.agriclutureassistant.pojo.HourlyWeather;
import com.example.agriclutureassistant.ui.ViewModel;
import com.example.agriclutureassistant.ui.adapters.NTimesAdapter;
import com.example.agriclutureassistant.ui.adapters.WeatherAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;

public class Weather extends Fragment {
    private ProgressBar bar;
    private ImageView weatherImg;
    private ViewModel viewModel;
    private TextView currentTemper, maxTemper, minTemper, windSpeed, humidity, rain;
    private WeatherAdapter adapter;
    private NTimesAdapter forecastAdapter;
    private final List<HourlyWeather> list_hourly = new ArrayList<>();
    private final List<WeeklyWeather> list_weekly = new ArrayList<>();
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
        recycler_weekly = view.findViewById(R.id.wheatherrecycler);
        recycler_hourly = view.findViewById(R.id.recyclernext);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_weekly.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManagernext
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_hourly.setLayoutManager(layoutManagernext);
        list_hourly.add(new HourlyWeather("45º", "16:30"));
        list_hourly.add(new HourlyWeather("45º", "16:30"));
        list_hourly.add(new HourlyWeather("45º", "16:30"));
        list_hourly.add(new HourlyWeather("45º", "16:30"));
        list_hourly.add(new HourlyWeather("45º", "16:30"));
        list_hourly.add(new HourlyWeather("45º", "16:30"));
        list_hourly.add(new HourlyWeather("45º", "16:30"));

        adapter = new WeatherAdapter(view.getContext(), list_hourly);
        recycler_weekly.setAdapter(adapter);
        list_weekly.add(new WeeklyWeather("Saturday", "23º"));
        list_weekly.add(new WeeklyWeather("Sunday", "25º"));
        list_weekly.add(new WeeklyWeather("Monday", "20º"));
        list_weekly.add(new WeeklyWeather("Thursday", "23º"));
        list_weekly.add(new WeeklyWeather("Friday", "29º"));
        list_weekly.add(new WeeklyWeather("Saturday", "26º"));
        list_weekly.add(new WeeklyWeather("Sunday", "18º"));
        forecastAdapter = new NTimesAdapter(view.getContext(), list_weekly);
        recycler_hourly.setAdapter(forecastAdapter);
        getCurrentDetails(view);
    }

    private void getCurrentDetails(View v) {
        definingViews(v);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getCurrentTemper();
        viewModel.livedata.observe(getActivity(), new Observer<WeatherModel.Root>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(WeatherModel.Root root) {
                bar.setVisibility(View.INVISIBLE);
                currentTemper.setText((int) (Math.round(root.current.temp_c)) + "º");
                Picasso.get().load("https:" + root.current.condition.icon).into(weatherImg);
                maxTemper.setText(Math.round(root.forecast.forecastday.get(0).day.maxtemp_c) + "º");
                minTemper.setText(Math.round(root.forecast.forecastday.get(0).day.mintemp_c) + "º");
                windSpeed.setText(Math.round(root.current.wind_kph) + " km/h");
                humidity.setText(root.current.humidity + "%");
                rain.setText(Math.round(root.forecast.forecastday.get(0).day.daily_chance_of_rain) + "%");
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
    }
}
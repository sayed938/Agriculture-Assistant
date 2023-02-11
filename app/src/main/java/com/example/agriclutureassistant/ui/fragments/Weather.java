package com.example.agriclutureassistant.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.pojo.NextForecastModel;
import com.example.agriclutureassistant.pojo.WeatherModel;
import com.example.agriclutureassistant.ui.adapters.NTimesAdapter;
import com.example.agriclutureassistant.ui.adapters.WeatherAdapter;

import java.util.ArrayList;
import java.util.List;


public class Weather extends Fragment {

    WeatherAdapter adapter;
    NTimesAdapter forecastAdapter;
    List<WeatherModel> list=new ArrayList<>();
    List<NextForecastModel>list2=new ArrayList<>();
    RecyclerView recyclerView,recyclerViewnext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_weather, container, false);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.wheatherrecycler);
        recyclerViewnext=view.findViewById(R.id.recyclernext);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManagernext
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewnext.setLayoutManager(layoutManagernext);
        list.add(new WeatherModel("45º","16:30"));
        list.add(new WeatherModel("45º","16:30"));
        list.add(new WeatherModel("45º","16:30"));
        list.add(new WeatherModel("45º","16:30"));
        list.add(new WeatherModel("45º","16:30"));
        list.add(new WeatherModel("45º","16:30"));
        list.add(new WeatherModel("45º","16:30"));

        adapter=new WeatherAdapter(view.getContext(),list);
        recyclerView.setAdapter(adapter);
        list2.add(new NextForecastModel("Saturday","23º"));
        list2.add(new NextForecastModel("Sunday","25º"));
        list2.add(new NextForecastModel("Monday","20º"));
        list2.add(new NextForecastModel("Thrusday","23º"));
        list2.add(new NextForecastModel("Friday","29º"));
        list2.add(new NextForecastModel("Saturday","26º"));
        list2.add(new NextForecastModel("Sunday","18º"));
        forecastAdapter=new NTimesAdapter(view.getContext(),list2);
        recyclerViewnext.setAdapter(forecastAdapter);
    }
}
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
import android.widget.TextView;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.pojo.HomeFeaturesModel;
import com.example.agriclutureassistant.ui.adapters.HomeFeaturesAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;


public class HomeFeatures extends Fragment {


    private RecyclerView featuresRecycler;
    private ArrayList<HomeFeaturesModel> list = new ArrayList<>();
    private HomeFeaturesAdapter adapter;
    private TextView date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_home_features, container, false);

        //Assigning variables
        featuresRecycler = view.findViewById(R.id.recyclerView);
        featuresRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        featuresRecycler.setHasFixedSize(true);
        date=view.findViewById(R.id.date_txt);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        date.setText(localDate());
        list = fillFeatures();
        adapter = new HomeFeaturesAdapter(list,getContext());
        featuresRecycler.setAdapter(adapter);


    }

    public ArrayList<HomeFeaturesModel> fillFeatures()
    {
        ArrayList<HomeFeaturesModel> list = new ArrayList<>();
        HomeFeaturesModel place1 = new HomeFeaturesModel("https://images.pexels.com/photos/1391503/pexels-photo-1391503.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","Plant Name");
        HomeFeaturesModel place2 = new HomeFeaturesModel("https://media.istockphoto.com/id/1277014376/photo/the-hand-in-a-garden-glove-holds-a-diseased-leaf-of-a-rose-plant-disease-fungal-leaves-spot.jpg?s=612x612&w=0&k=20&c=fM-I75PtLvDL-plpR5g3VDn_5llisfN7PCfpbovhmJE=","Plant Disease");
        HomeFeaturesModel place3 = new HomeFeaturesModel("https://media.istockphoto.com/id/1009934102/photo/were-all-responsible-for-creating-a-better-tomorrow.jpg?s=612x612&w=0&k=20&c=ap3hWDhcFsroOWj41CRaaMKDzEofQ9FcYypWDobVwis=","Social Media");
        list.addAll(Arrays.asList(new HomeFeaturesModel[]{place1,place2,place3}));
        return list;
    }
    public String localDate(){
        String date="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate localDate=LocalDate.now();
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
           date=  localDate.format(formatter);
        }
        return date;
    }
}
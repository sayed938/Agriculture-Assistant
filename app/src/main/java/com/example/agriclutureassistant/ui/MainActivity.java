package com.example.agriclutureassistant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.agriclutureassistant.ProjectData;
import com.example.agriclutureassistant.ui.pojo.UserSignUpData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.agriclutureassistant.R;

public class MainActivity extends AppCompatActivity {
    NavController navcontroller;
    BottomNavigationView bottomview;
    NavHostFragment navHostFragment;
    public Context context;

    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(ProjectData.filename,Context.MODE_PRIVATE);


        context = getApplicationContext();
        bottomview = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_hostfragment);

        //Pass the ID's of Different destinations
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home_menu, R.id.weather_menu, R.id.hard_menu, R.id.setting_menu)
                .build();

        //Initialize NavController.
        navcontroller = (NavController) navHostFragment.getNavController();
        //NavigationUI.setupActionBarWithNavController(this, navcontroller, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomview, navcontroller);


    }

}
package com.example.agriclutureassistant.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;

import com.example.agriclutureassistant.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
 NavController navcontroller;
 BottomNavigationView bottomview;
NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       bottomview =(BottomNavigationView) findViewById(R.id.bottomNavigationView);
       navHostFragment=(NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_hostfragment);

        //Pass the ID's of Different destinations
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home_menu, R.id.weather_menu, R.id.hard_menu, R.id.setting_menu )
                .build();

        //Initialize NavController.
         navcontroller = (NavController) navHostFragment.getNavController();
        //NavigationUI.setupActionBarWithNavController(this, navcontroller, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomview, navcontroller);


    }
}
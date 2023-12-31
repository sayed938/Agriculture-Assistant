package com.example.agriclutureassistant.ui.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.agriclutureassistant.ProjectData;
import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.ui.MainActivity;
import com.example.agriclutureassistant.ui.features_activities.Sign_in;
import com.example.agriclutureassistant.ui.features_activities.Signup;

public class HomePage extends AppCompatActivity {

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        findViewById(R.id.join_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, Sign_in.class));
                finish();

            }
        });
        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, Signup.class));
                finish();
            }
        });



        sharedPreferences = getSharedPreferences(ProjectData.filename, Context.MODE_PRIVATE);
        //Checking if the user still logging or not.
        if (sharedPreferences.contains(ProjectData.email_)){

            startActivity(new Intent(HomePage.this, MainActivity.class));
            finish();
        }
    }

}
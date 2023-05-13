package com.example.agriclutureassistant.ui.features_activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.data.RemoteRequestPlants;

import java.net.URI;

public class PlantNameDetails extends AppCompatActivity {
    private ImageView plantImg;
    private TextView plant,plantDetails,overview;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_name_details);
        plantImg = findViewById(R.id.plant_img);
        plantDetails=findViewById(R.id.plant_details);
        overview=findViewById(R.id.overview);
        plant=findViewById(R.id.plant);
        plant.setText(getIntent().getExtras().getString("plant_nameC"));
        getIntentPlant();
    }

    private void getIntentPlant() {
        Bitmap plant_camera = (Bitmap) getIntent().getExtras().get("plant");
        Uri plant_gallery = (Uri) getIntent().getExtras().get("plant_gallery");
        plantImg.setImageBitmap(plant_camera);
        plantImg.setImageURI(plant_gallery);
        plant.setText(getIntent().getExtras().getString("plant_nameC"));
        plantDetails.setText(getIntent().getExtras().getString("plant_details"));
        overview.setText(getIntent().getExtras().getString("overview"));
    }

}
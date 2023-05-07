package com.example.agriclutureassistant.ui.features_activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.data.RemoteRequestPlants;
import com.example.agriclutureassistant.pojo.PlantsDiseases;
import com.example.agriclutureassistant.pojo.PlantsTypes;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantDisease extends AppCompatActivity {
    private LinearLayout bt_cameraDisease, bt_galleryDisease;
    String name, disease;
    ProgressBar bar;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_disease);
        bar = findViewById(R.id.progressBar_disease);
        bt_cameraDisease = findViewById(R.id.bt_cameraPlantDisease);
        bt_galleryDisease = findViewById(R.id.bt_galleryPlantDisease);
        bt_cameraDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
        bt_galleryDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, 101);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            postPlantImage((Bitmap) data.getExtras().get("data"), null);
        } else if (requestCode == 101 && resultCode == RESULT_OK) {
            postPlantImage(null, data.getData());
        }
    }

    private void postPlantImage(Bitmap bitmap, Uri uri) {
        HashMap<Object, Object> plant_map = new HashMap<>();
        if (bitmap == null) {
            plant_map.put("file", uri);
        } else if (uri == null) {
            plant_map.put("file", bitmap);
        }
        plantResponse(plant_map, bitmap, uri);
    }

    private void plantResponse(HashMap<Object, Object> plant_map, Bitmap cameraPhoto, Uri galleryPhoto) {
        bar.setVisibility(View.VISIBLE);
        RemoteRequestPlants requestPlants = new RemoteRequestPlants();
        Call<PlantsDiseases> callPlant = requestPlants.apiService().postPlantDisease(plant_map);
        callPlant.enqueue(new Callback<PlantsDiseases>() {
            @Override
            public void onResponse(Call<PlantsDiseases> call, Response<PlantsDiseases> response) {

                try {
                    name = response.body().name;
                    disease = response.body().date;
                    Intent intent = new Intent(getApplication(), PlantNameDetails.class);
                    if (cameraPhoto == null) {
                        intent.putExtra("plant_gallery", galleryPhoto);
                    } else if (galleryPhoto == null) {
                        intent.putExtra("plant", cameraPhoto);
                    }
                    intent.putExtra("plant_nameC", name);
                    intent.putExtra("plant_details", disease);
                    intent.putExtra("overview", "Overview");
                    bar.setVisibility(View.INVISIBLE);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d("PlantDisease", e.getMessage());
                    bar.setVisibility(View.INVISIBLE);
                    Toast.makeText(PlantDisease.this, "Try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlantsDiseases> call, Throwable t) {
                Log.d("PlantDisease", t.getMessage());
                bar.setVisibility(View.INVISIBLE);
                Toast.makeText(PlantDisease.this, "Try again", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
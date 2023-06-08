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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.data.RemoteRequestPlants;
import com.example.agriclutureassistant.ui.pojo.PlantsDiseases;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantDisease extends AppCompatActivity {
    private LinearLayout bt_cameraDisease, bt_galleryDisease;
    String name, disease;
    ProgressBar bar;
    MultipartBody.Part part;

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

        } else if (requestCode == 101 && resultCode == RESULT_OK) {
            String imagePath = new PlantName().getRealPathFromURI(data.getData());
            File image = new File(imagePath);
            RequestBody request = RequestBody.create(MediaType.parse("image/*"), image);
            part = MultipartBody.Part.createFormData("images", image.getName() + request);
            plantResponse(null, data.getData());
        }
    }


    private void plantResponse(Bitmap cameraPhoto, Uri galleryPhoto) {
        bar.setVisibility(View.VISIBLE);
        RemoteRequestPlants requestPlants = new RemoteRequestPlants();
        Call<PlantsDiseases> callPlant = requestPlants.apiService().postPlantDisease(part);
        callPlant.enqueue(new Callback<PlantsDiseases>() {
            @Override
            public void onResponse(Call<PlantsDiseases> call, Response<PlantsDiseases> response) {

                try {
                    name = response.body().name;
                    disease = response.body().date;
                    responseIntent(cameraPhoto,galleryPhoto,name,disease);
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
                Toast.makeText(PlantDisease.this, "Try again ! Connection error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void responseIntent(Bitmap cameraPhoto, Uri galleryPhoto, String name,String overView) {
        Intent intent = new Intent(getApplication(), PlantNameDetails.class);
        if (galleryPhoto == null) {
            intent.putExtra("plant", cameraPhoto);
        } else if (cameraPhoto == null) {
            intent.putExtra("plant_gallery", galleryPhoto);
        }
        intent.putExtra("plant_nameC", name);
        intent.putExtra("plant_details", "");
        intent.putExtra("overview", overView);
        bar.setVisibility(View.INVISIBLE);
        startActivity(intent);
    }
}
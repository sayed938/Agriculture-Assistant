package com.example.agriclutureassistant.ui.features_activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.data.RemoteRequestPlants;
import com.example.agriclutureassistant.ui.pojo.PlantsDiseases;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
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
            Bitmap b = (Bitmap) data.getExtras().get("data");
            imagePath(b);
            plantResponse((Bitmap) data.getExtras().get("data"), null);
        } else if (requestCode == 101 && resultCode == RESULT_OK) {
            String imagePath = getRealPathFromURI(data.getData());
            File image = new File(imagePath);
            RequestBody request = RequestBody.create(MediaType.parse("image/*"), image);
            part = MultipartBody.Part.createFormData("file", image.getName(), request);
            plantResponse(null, data.getData());
        }
    }


    private void plantResponse(Bitmap cameraPhoto, Uri galleryPhoto) {
        bar.setVisibility(View.VISIBLE);
        RemoteRequestPlants requestPlants = new RemoteRequestPlants();
        Call<PlantsDiseases> callPlant = requestPlants.apiService1().postPlantDisease(part);
        callPlant.enqueue(new Callback<PlantsDiseases>() {
            @Override
            public void onResponse(Call<PlantsDiseases> call, Response<PlantsDiseases> response) {

                try {
                    name = response.body().name;
                    disease = response.body().date;
                    responseIntent(cameraPhoto, galleryPhoto, name, disease);
                } catch (Exception e) {
                    Log.d("PlantDisease", e.getMessage() + " OnResponse");
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

    private void responseIntent(Bitmap cameraPhoto, Uri galleryPhoto, String name, String overView) {
        Intent intent = new Intent(getApplication(), PlantNameDetails.class);
        if (galleryPhoto == null) {
            intent.putExtra("plant", cameraPhoto);
        } else if (cameraPhoto == null) {
            intent.putExtra("plant_gallery", galleryPhoto);
        }
        intent.putExtra("plant_nameC", name);
        intent.putExtra("plant_details", overView);
        intent.putExtra("overview", "Overview");
        bar.setVisibility(View.INVISIBLE);
        startActivity(intent);
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }
    private void imagePath(Bitmap b) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Title", null);
        Uri uri = Uri.parse(path);
        String imagePath1 = getRealPathFromURI(uri);
        File image = new File(imagePath1);
        RequestBody request = RequestBody.create(MediaType.parse("image/*"), image);
        part = MultipartBody.Part.createFormData("file", image.getName(), request);
    }
}
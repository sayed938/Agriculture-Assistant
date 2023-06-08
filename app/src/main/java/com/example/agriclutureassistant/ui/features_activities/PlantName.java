package com.example.agriclutureassistant.ui.features_activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.data.RemoteRequestPlants;
import com.example.agriclutureassistant.ui.pojo.PlantsTypes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantName extends AppCompatActivity {
    LinearLayout bt_camera, bt_gallery;
    ProgressBar bar;
    List<MultipartBody.Part> parts = new ArrayList<>();
    List<RequestBody> imageRequests = new ArrayList<>();
    private static final String TAG = "PlantName";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_name);
        bar = findViewById(R.id.progressBar_plantName);
        bt_camera = findViewById(R.id.bt_cameraPlantName);
        bt_gallery = findViewById(R.id.bt_galleryPlantName);
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
        bt_gallery.setOnClickListener(new View.OnClickListener() {
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
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap b = (Bitmap) data.getExtras().get("data");
            b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Title", null);
            Uri uri = Uri.parse(path);
            String imagePath = getRealPathFromURI(uri);
            imagePath(imagePath);
            plantResponse((Bitmap) data.getExtras().get("data"), null);

        } else if (requestCode == 101 && resultCode == RESULT_OK) {
            String imagePath = getRealPathFromURI(data.getData());
            imagePath(imagePath);
            plantResponse(null, data.getData());
        }
    }

    private void plantResponse(Bitmap cameraPhoto, Uri galleryPhoto) {
        bar.setVisibility(View.VISIBLE);
        RemoteRequestPlants requestPlants = new RemoteRequestPlants();
        Call<PlantsTypes.Root> callPlant = requestPlants.apiService().postPlant(parts, "2b10nmypxhmUxa1oSXvmsLKQpu");
        callPlant.enqueue(new Callback<PlantsTypes.Root>() {
            @Override
            public void onResponse(Call<PlantsTypes.Root> call, Response<PlantsTypes.Root> response) {
                try {
                    parts.remove(0);
                    imageRequests.remove(0);
                    String name = "";
                    name = response.body().results.get(0).species.commonNames.get(0);
                    responseIntent(cameraPhoto, galleryPhoto, name);

                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    bar.setVisibility(View.INVISIBLE);
                    Toast.makeText(PlantName.this, "Try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlantsTypes.Root> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                bar.setVisibility(View.INVISIBLE);
                Toast.makeText(PlantName.this, "Try again ! Connection error ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void imagePath(String imagePath) {
        if(parts.size()>0)
           parts.remove(0);
        File image = new File(imagePath);
        RequestBody request = RequestBody.create(MediaType.parse("image/*"), image);
        imageRequests.add(request);

        for (int i = 0; i < imageRequests.size(); i++) {
            MultipartBody.Part part = MultipartBody.Part.createFormData("images", image.getName() + i, imageRequests.get(i));
            parts.add(part);
        }
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

    private void responseIntent(Bitmap cameraPhoto, Uri galleryPhoto, String name) {
        Intent intent = new Intent(getApplication(), PlantNameDetails.class);
        if (galleryPhoto == null) {
            intent.putExtra("plant", cameraPhoto);
        } else if (cameraPhoto == null) {
            intent.putExtra("plant_gallery", galleryPhoto);
        }
        intent.putExtra("plant_nameC", name);
        intent.putExtra("plant_details", "");
        intent.putExtra("overview", "");
        bar.setVisibility(View.INVISIBLE);
        startActivity(intent);
    }
}

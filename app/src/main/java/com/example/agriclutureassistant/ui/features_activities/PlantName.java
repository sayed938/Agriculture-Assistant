package com.example.agriclutureassistant.ui.features_activities;

import static android.content.ContentValues.TAG;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.tv.TvContract;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.data.RemoteRequestPlants;
import com.example.agriclutureassistant.data.SocialApiService;
import com.example.agriclutureassistant.pojo.PlantsTypes;
import com.google.firebase.firestore.proto.TargetGlobal;

import java.net.URI;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlantName extends AppCompatActivity {
    LinearLayout bt_camera, bt_gallery;
    String name = "";
    ProgressBar bar;
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
        Call<PlantsTypes> callPlant = requestPlants.apiService().postPlant(plant_map);
        callPlant.enqueue(new Callback<PlantsTypes>() {
            @Override
            public void onResponse(Call<PlantsTypes> call, Response<PlantsTypes> response) {
                try {
                    name = response.body().data;
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
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    bar.setVisibility(View.INVISIBLE);
                    Toast.makeText(PlantName.this, "Try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlantsTypes> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                bar.setVisibility(View.INVISIBLE);
                Toast.makeText(PlantName.this, "Try again ", Toast.LENGTH_SHORT).show();
            }
        });

    }


}




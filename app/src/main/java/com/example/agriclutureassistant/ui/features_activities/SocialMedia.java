package com.example.agriclutureassistant.ui.features_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.pojo.PostModel;
import com.example.agriclutureassistant.ui.adapters.PostsAdapter;
import com.example.agriclutureassistant.ui.viewmodel.PostViewModel;

import java.util.ArrayList;
import java.util.List;

public class SocialMedia extends AppCompatActivity {
    RecyclerView recyclerView;
    List<PostModel> post_list = new ArrayList<>();
    private PostViewModel postViewModel = new PostViewModel();
    private static final String TAG = "SocialMedia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        findViewById(R.id.add_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SocialMedia.this, AddPost.class));
            }
        });


        recyclerView = findViewById(R.id.posts);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        try {
            getAllPosts();
        } catch (Exception e) {
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }

    }

    public void getAllPosts() {

        postViewModel.getAllPosts();
        postViewModel.getGetAllPostsALiveData().observe(this, new Observer<List<PostModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<PostModel> postModels) {
                post_list = postModels;
                recyclerView.setAdapter(new PostsAdapter(post_list));
                //new PostsAdapter(post_list).notifyDataSetChanged();
            }
        });
    }

}
package com.example.agriclutureassistant.ui.features_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.pojo.PostsModel;
import com.example.agriclutureassistant.ui.adapters.PostsAdapter;

import java.util.ArrayList;
import java.util.List;

public class SocialMedia extends AppCompatActivity {
RecyclerView recyclerView;
List<PostsModel>post_list=new ArrayList<>();
    Button addpost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
       findViewById(R.id.add_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SocialMedia.this,AddPost.class));

            }
        });



        recyclerView=findViewById(R.id.posts);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        post_list.add(new PostsModel("Sayed Samir","I want some seeds to plant a piece of land I ask you to help me with the crop that I grow in this weather so that it is excellent"));
        post_list.add(new PostsModel("Shady elfla7","I want some seeds to plant a piece of land\n" +
                "I ask you to help me with the crop that I grow in this weather so that it is excellent I grow in this weather so that it is excellent"));
        post_list.add(new PostsModel("Pola elpola","I want some seeds to plant a piece of land I ask you to help me with the crop that I grow in this weather so that it is excellent"));
        post_list.add(new PostsModel("Mahrous el gd3","I want some seeds to plant a piece of land\n" +
                "I ask you to help me with the crop that I grow in this weather so that it is excellent I want some seeds to plant a piece of land I ask you to help me with the crop that I grow in this weather so that it is excellent"));
        post_list.add(new PostsModel("mohamed shady","I want some seeds to plant a piece of land\n" +
                "I ask you to help me with the crop that I grow in this weather so that it is excellent"));
        post_list.add(new PostsModel("Eslam Samir","I want some seeds to plant a piece of land\n" +
                "I ask you to help me with the crop that I grow in this weather so that it is excellent"));
        post_list.add(new PostsModel("bassem hossam","I want some seeds to plant a piece of land\n" +
                "I ask you to help me with the crop that I grow in this weather so that it is excellent"));
        post_list.add(new PostsModel("mohamed khaled","اريد بعض البذور لزراعة قطعه من الارض \n" +
                "اطلب منكم مساعدتي في المحصول التي ازرعه في هذا الطقس لكي يكون ممتاز"));
        post_list.add(new PostsModel("Ahmed Samir","I want some seeds to plant a piece of land\n" +
                "I ask you to help me with the crop that I grow in this weather so that it is excellent"));
        post_list.add(new PostsModel("Sayed Samir Attia ","I want some seeds to plant a piece of land\n" +
                "I ask you to help me with the crop that I grow in this weather so that it is excellent"));
        post_list.add(new PostsModel("Shady Mo","I want some seeds to plant a piece of land\n" +
                "I ask you to help me with the crop that I grow in this weather so that it is excellent"));
        post_list.add(new PostsModel("Sayed Samir","I want some seeds to plant a piece of land\n" +
                "I ask you to help me with the crop that I grow in this weather so that it is excellent"));
        post_list.add(new PostsModel("mo Samir","I want some seeds to plant a piece of land\n" +
                "I ask you to help me with the crop that I grow in this weather so that it is excellent"));
         recyclerView.setAdapter(new PostsAdapter(post_list));
    }

}
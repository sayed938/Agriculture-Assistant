package com.example.agriclutureassistant.ui.features_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.pojo.CommentModel;
import com.example.agriclutureassistant.pojo.PostModel;
import com.example.agriclutureassistant.ui.adapters.CommentAdapter;
import com.example.agriclutureassistant.ui.viewmodel.PostViewModel;

import java.util.ArrayList;
import java.util.List;

public class Comments extends AppCompatActivity {

    private PostViewModel postViewModel = new PostViewModel();
    private List<PostModel> commentsList = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private CommentModel commentModel;
    private RecyclerView commentsRecyclerView;
    private static final String TAG = "Comments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        String post_id = getIntent().getExtras().getString("postid");
        commentModel = new CommentModel(post_id, "polahany");

        getAllComments();

        findViewById(R.id.back_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getAllComments() {


        postViewModel.getAllComments(commentModel);
        postViewModel.getCommentsLiveData().observe(this, new Observer<List<CommentModel>>() {
            @Override
            public void onChanged(List<CommentModel> commentModels) {
                if(commentModels != null){
                    commentAdapter = new CommentAdapter(commentModels);
                    commentsRecyclerView.setAdapter(commentAdapter);
                }else {
                    Toast.makeText(Comments.this, "No comments yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
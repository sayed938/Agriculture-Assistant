package com.example.agriclutureassistant.ui.features_activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.data.SocialApiService;
import com.example.agriclutureassistant.pojo.AddComment;
import com.example.agriclutureassistant.pojo.CommentModel;
import com.example.agriclutureassistant.pojo.PostModel;
import com.example.agriclutureassistant.ui.adapters.CommentAdapter;
import com.example.agriclutureassistant.ui.viewmodel.PostViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Comments extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String userId;
    private PostViewModel postViewModel = new PostViewModel();
    private List<CommentModel> comment_m = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private CommentModel commentModel;
    private RecyclerView commentsRecyclerView;
    private static final String TAG = "Comments";
    EditText user_post;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        String post_id = getIntent().getExtras().getString("postid");
        user_post=findViewById(R.id.write_post);
        findViewById(R.id.post_post).setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                if(user_post.getText().length()==0){
                    Toast.makeText(Comments.this, "please write correctly", Toast.LENGTH_SHORT).show();
                }
                else {
                    setComment(user_post.getText().toString(),post_id);
                    user_post.setText("");
                }

            }
        });
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        commentModel = new CommentModel(post_id, "polahany");

        getAllComments();

        findViewById(R.id.back_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Firebase caching
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        firebaseFirestore.setFirestoreSettings(settings);
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
    public void setComment(String text,String post_id) {

        userId = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userId);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                postComment(value.getString("username"),text,post_id);
            }
        });

    }
    private void postComment(String user_name,String text,String post_id){
        HashMap<Object, Object> comment_map = new HashMap<>();
        comment_map.put("user_name", user_name);
        comment_map.put("text",text);
        comment_map.put("post_id", post_id);
        comment_map.put("Password", "polahany");
        buildComment(comment_map);

    }
    private  void buildComment( HashMap<Object, Object> comment_map){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://talabaklebanon.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SocialApiService post = retrofit.create(SocialApiService.class);
        Call<AddComment> getModelCall = post.postComments(comment_map);
        getModelCall.enqueue(new Callback<AddComment>() {
            @Override
            public void onResponse(Call<AddComment> call, Response<AddComment> response) {
                getAllComments();
            }
            @Override
            public void onFailure(Call<AddComment> call, Throwable t) {

            }
        });
    }
}
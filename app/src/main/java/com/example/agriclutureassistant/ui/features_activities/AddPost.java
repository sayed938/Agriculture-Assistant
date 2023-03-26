package com.example.agriclutureassistant.ui.features_activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.data.SocialApiService;
import com.example.agriclutureassistant.pojo.PostModel;
import com.example.agriclutureassistant.ui.viewmodel.PostViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPost extends AppCompatActivity {

    private EditText edt_post;
    private Button post_button;
    private PostModel postModel;
    private String pass = "polahany";
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    private String userId;
    private String userName = null;
    private PostViewModel postViewModel = new PostViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        edt_post = findViewById(R.id.text_post);
        post_button = findViewById(R.id.post_button);


        settingUserName();

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edt_post.getText().toString().trim())) {
                    edt_post.setError("Invalid");
                    edt_post.requestFocus();
                } else {
                    postModel = new PostModel(userName, edt_post.getText().toString(), pass);
                    postViewModel.setPost(postModel);
                    edt_post.setText("");
                    startActivity(new Intent(getApplicationContext(),SocialMedia.class));
                }
            }
        });

    }
    public void settingUserName() {

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userName = value.getString("username");
            }
        });
    }
}
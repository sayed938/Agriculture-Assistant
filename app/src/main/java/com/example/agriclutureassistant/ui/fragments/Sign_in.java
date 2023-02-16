package com.example.agriclutureassistant.ui.fragments;

import static java.security.AccessController.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.AccessController;

public class Sign_in extends AppCompatActivity {

    private EditText edt_email,edt_pass;
    private Button login_btn;
    private TextView forgot_pass,go_to_signUp;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);



        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");

        //Assign Variables
        assignLoginVariables();

        //Going back to signUp fragment
        go_to_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sign_in.this, Signup.class));
            }
        });

        //Validation & Login
        clickToLogin();

        resetPassword();
    }

    public void assignLoginVariables()
    {
        go_to_signUp = findViewById(R.id.back_signup);
        edt_email = findViewById(R.id.edt_login_email);
        edt_pass = findViewById(R.id.edt_login_password);
        forgot_pass = findViewById(R.id.forgot_password);
        login_btn = findViewById(R.id.signIn_login_btn);
    }

    public void clickToLogin()
    {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edt_email.getText().toString().trim();
                String pass = edt_pass.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    edt_email.setError("Email is required");
                    edt_email.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    edt_email.setError("Valid email is required");
                    edt_email.requestFocus();
                }
                else if(TextUtils.isEmpty(pass))
                {
                    edt_pass.setError("Password is required");
                    edt_pass.requestFocus();
                }
                else {
                    loginUser(email,pass);
                }
            }
        });
    }

    public void loginUser(String email,String pass)
    {
        progressDialog.show();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void resetPassword()
    {

            forgot_pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email = edt_email.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        edt_email.setError("email required");
                    } else {
                        progressDialog.show();
                        firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.sendPasswordResetEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful())
                                        {
                                            progressDialog.cancel();
                                            Toast.makeText(getApplicationContext(),"Email sent",Toast.LENGTH_SHORT).show();

                                        }else {
                                            progressDialog.cancel();
                                            Toast.makeText(getApplicationContext(),"Email dose not exist",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            });
    }
}
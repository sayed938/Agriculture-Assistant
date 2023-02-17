package com.example.agriclutureassistant.ui.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.pojo.UserSignUpData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup extends AppCompatActivity {

    private EditText edt_name, edt_email, edt_phone, edt_pass;
    private Button signUp_btn;
    private TextView signIn;
    private String name, email, password, mobile;
    private static final String TAG = "Signup";
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");

        //Calling assignVariables fun
        assignVariables();
        //Validation & Register process
        clickingToRegister();


    }

    public void assignVariables() {
        edt_email = findViewById(R.id.edt_register_email);
        edt_name = findViewById(R.id.edt_register_username);
        edt_pass = findViewById(R.id.edt_register_password);
        edt_phone = findViewById(R.id.edt_register_phone);
        signUp_btn = findViewById(R.id.signup_register_btn);
        signIn = findViewById(R.id.signin_register_txt_view);
    }

    public void clickingToRegister() {
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //filling string variables
                name = edt_name.getText().toString();
                password = edt_pass.getText().toString();
                email = edt_email.getText().toString();
                mobile = edt_phone.getText().toString();

                //Validation email,name,password and mobile number
                if (TextUtils.isEmpty(name)) {
                    edt_name.setError("Full name is required");
                    edt_name.requestFocus();
                } else if (TextUtils.isEmpty(email)) {
                    edt_email.setError("Email is required");
                    edt_email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edt_email.setError("Email address is not valid");
                    edt_email.requestFocus();
                } else if (TextUtils.isEmpty(mobile)) {
                    edt_phone.setError("phone number is required");
                    edt_phone.requestFocus();
                } else if (mobile.length() < 11) {
                    edt_phone.setError("phone number is not valid");
                    edt_phone.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    edt_pass.setError("Password is required");
                    edt_pass.requestFocus();
                } else if (password.length() < 6) {
                    edt_pass.setError("Password should be more than 6 digits.");
                    edt_pass.requestFocus();
                } else {
                    registerUser(email, password, name, mobile);
                }

            }
        });
    }

    private void registerUser(String email, String password, String name, String mobile) {

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        progressDialog.cancel();
                        sendEmailVerification();

                        firebaseFirestore.collection("Users")
                                .document(FirebaseAuth.getInstance().getUid())
                                .set(new UserSignUpData(name, mobile, email));
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

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Verification email is sent, Verify and login again", Toast.LENGTH_LONG).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(Signup.this, Sign_in.class));
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Failed to send verification email", Toast.LENGTH_LONG).show();

        }
    }
}
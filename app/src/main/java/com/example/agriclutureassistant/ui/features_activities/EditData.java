package com.example.agriclutureassistant.ui.features_activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriclutureassistant.ProjectData;
import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.ui.pojo.UserSignUpData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class EditData extends AppCompatActivity {

    EditText edt_username, edt_phone, edt_pass;
    TextView edt_email, headName;
    Button save_btn;
    //firebase area
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userId;
    FirebaseUser firebaseUser;
    private static final String TAG = "EditData";
    AuthCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        assignVar();

        //Setting user data into editTexts
        settingUserData();




        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangingDataAlertDialog();
            }
        });

    }

    public void assignVar() {
        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        edt_username = findViewById(R.id.edt_username);
        edt_phone = findViewById(R.id.edt_phone);
        save_btn = findViewById(R.id.save_data_btn);
        //headName = findViewById(R.id.nameTV);
    }

    public void settingUserData() {
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                edt_email.setText(value.getString("email"));
                edt_phone.setText(value.getString("phone"));
                edt_username.setText(value.getString("username"));
                //headName.setText(value.getString("username"));
                edt_pass.setText(value.getString("password"));
                credential = EmailAuthProvider
                        .getCredential(value.getString("email"), value.getString("password"));
            }
        });


    }

    public void editingData() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        String name = edt_username.getText().toString().trim();
        String phone = edt_phone.getText().toString().trim();
        String pass = edt_pass.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (TextUtils.isEmpty(name)) {
            edt_username.setError("Full name is required");
            edt_username.requestFocus();
        } else if (TextUtils.isEmpty(phone)) {
            edt_phone.setError("phone number is required");
            edt_phone.requestFocus();
        } else if (phone.length() < 11) {
            edt_phone.setError("phone number is not valid");
            edt_phone.requestFocus();
        } else if (TextUtils.isEmpty(pass)) {
            edt_pass.setError("Password is required");
            edt_pass.requestFocus();
        } else if (pass.length() < 6) {
            edt_pass.setError("Password should be more than 6 digits.");
            edt_pass.requestFocus();
        } else {


            firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        firebaseUser.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful())
                                    Log.d(TAG, "fire: pass updated");
                                else
                                    Log.d(TAG, "fire: error pass not updated");
                            }
                        });
                    } else {
                        Log.d(TAG, "fire: Error auth failed");
                    }
                }
            });

            firebaseAuth.updateCurrentUser(firebaseUser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            firebaseFirestore.collection("Users")
                                    .document(userId)
                                    .set(new UserSignUpData(name, phone, email, pass));

                            Toast.makeText(EditData.this, "Saved", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(EditData.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    public void showChangingDataAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm changes");
        builder.setMessage("Change your data?");
        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editingData();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.create().hide();
            }
        });
        builder.create().show();
    }
}
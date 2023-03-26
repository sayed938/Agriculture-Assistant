package com.example.agriclutureassistant.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agriclutureassistant.ProjectData;
import com.example.agriclutureassistant.ui.features_activities.EditData;
import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.ui.features_activities.Sign_in;
import com.example.agriclutureassistant.ui.features_activities.Signup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class Settings extends Fragment {

    ImageView pen_to_edit;
    private Button sign_out;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userId;
    TextView txt_name,txt_phone,txt_email,txt_pass;
    //******//
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pen_to_edit = view.findViewById(R.id.pen_to_edit);
        sign_out = view.findViewById(R.id.signOut_btn);
        txt_email=view.findViewById(R.id.txt_view_email);
        txt_pass=view.findViewById(R.id.txt_view_password);
        txt_name=view.findViewById(R.id.txt_view_userName);
        txt_phone=view.findViewById(R.id.txt_view_phone);

        pen_to_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditData.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sharedPreferences = getActivity().getSharedPreferences(ProjectData.filename, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                getActivity().onBackPressed();
                startActivity(new Intent(requireActivity(), Sign_in.class));
                requireActivity().finish();
            }

        });

        settingUserData();
    }

    public void settingUserData()
    {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                txt_email.setText(value.getString("email"));
                txt_phone.setText(value.getString("phone"));
                txt_name.setText(value.getString("username"));
                txt_pass.setText(value.getString("password"));
            }
        });
    }
}
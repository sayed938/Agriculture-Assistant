package com.example.agriclutureassistant.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.pojo.BookUserData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;

import io.reactivex.internal.util.BackpressureHelper;

public class BookingComponent extends Fragment {

    private EditText edit_name, edit_phone, edit_address;
    private Button book_btn;
    private String name, phone, address;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_booking_component, container, false);

        edit_address = view.findViewById(R.id.edt_book_address);
        edit_name = view.findViewById(R.id.edt_book_name);
        edit_phone = view.findViewById(R.id.edt_book_phone);
        book_btn = view.findViewById(R.id.book_btn);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        bookClicking();
    }

    public void bookClicking() {

        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = edit_name.getText().toString().trim();
                phone = edit_phone.getText().toString().trim();
                address = edit_address.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    edit_name.setError("Full name is required");
                    edit_name.requestFocus();
                } else if (TextUtils.isEmpty(phone)) {
                    edit_phone.setError("phone number is required");
                    edit_phone.requestFocus();
                } else if (phone.length() < 11) {
                    edit_phone.setError("phone number is not valid");
                    edit_phone.requestFocus();
                } else if (TextUtils.isEmpty(address)) {
                    edit_address.setError("Password is required");
                    edit_address.requestFocus();
                } else {
                    setDataInCollection(name, address, phone);
                }

            }
        });
    }

    public void setDataInCollection(String name, String address, String phone) {


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("bookData")
                .document(FirebaseAuth.getInstance().getUid())
                .set(new BookUserData(name, phone, address))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Your form is sent", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.action_bookingComponent_to_hard_menu);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
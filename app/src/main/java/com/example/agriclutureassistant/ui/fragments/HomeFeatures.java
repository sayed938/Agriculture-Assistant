package com.example.agriclutureassistant.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.example.agriclutureassistant.pojo.HomeFeaturesModel;
import com.example.agriclutureassistant.pojo.WeatherModel;
import com.example.agriclutureassistant.ui.MainActivity;
import com.example.agriclutureassistant.ui.ViewModel;
import com.example.agriclutureassistant.ui.adapters.HomeFeaturesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class HomeFeatures extends Fragment {


    private RecyclerView featuresRecycler;
    private ArrayList<HomeFeaturesModel> list = new ArrayList<>();
    private HomeFeaturesAdapter adapter;
    private TextView date, day, temper;
    private ViewModel viewModel;
    private ProgressBar bar;
    //**********//
    private static final String TAG = "HomeFeatures";
    TextView user_name_tv;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_features, container, false);

        //Assigning variables
        user_name_tv = view.findViewById(R.id.user_name_tv);


        featuresRecycler = view.findViewById(R.id.recyclerView);
        featuresRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        featuresRecycler.setHasFixedSize(true);
        date = view.findViewById(R.id.date_txt);

        settingUserName();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bar = view.findViewById(R.id.main_progress_bar);
        day = view.findViewById(R.id.main_day);
        temper = view.findViewById(R.id.main_temper);
        date.setText(localDate());
        list = fillFeatures();
        adapter = new HomeFeaturesAdapter(list, getContext());
        featuresRecycler.setAdapter(adapter);
        day.setText(day(localDate()));
        Temper();



    }

    public ArrayList<HomeFeaturesModel> fillFeatures() {
        ArrayList<HomeFeaturesModel> list = new ArrayList<>();
        HomeFeaturesModel place1 = new HomeFeaturesModel("https://images.pexels.com/photos/1391503/pexels-photo-1391503.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Plant Name");
        HomeFeaturesModel place2 = new HomeFeaturesModel("https://media.istockphoto.com/id/1277014376/photo/the-hand-in-a-garden-glove-holds-a-diseased-leaf-of-a-rose-plant-disease-fungal-leaves-spot.jpg?s=612x612&w=0&k=20&c=fM-I75PtLvDL-plpR5g3VDn_5llisfN7PCfpbovhmJE=", "Plant Disease");
        HomeFeaturesModel place3 = new HomeFeaturesModel("https://media.istockphoto.com/id/1009934102/photo/were-all-responsible-for-creating-a-better-tomorrow.jpg?s=612x612&w=0&k=20&c=ap3hWDhcFsroOWj41CRaaMKDzEofQ9FcYypWDobVwis=", "Social Media");
        list.addAll(Arrays.asList(new HomeFeaturesModel[]{place1, place2, place3}));
        return list;
    }

    public static String localDate() {
        String date = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = localDate.format(formatter);
        }
        return date;
    }

    private String day(String date) {
        String day = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date datE = sdf.parse(date);
            sdf.applyPattern("EEEE");
            day = sdf.format(datE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    private void Temper() {
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getCurrentTemper();
        viewModel.livedataWeather1.observe(getActivity(), new Observer<WeatherModel.Root>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(WeatherModel.Root root) {
                temper.setText((int) (Math.round(root.current.temp_c)) + "º" + "C");
                bar.setVisibility(View.INVISIBLE);
            }
        });
    }


    public void settingUserName() {

        try {
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();
            userId = firebaseAuth.getCurrentUser().getUid();
            DocumentReference documentReference = firebaseFirestore.collection("Users").document(userId);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    user_name_tv.setText(value.getString("username"));
                }
            });
        }catch (Exception e){
            Log.d(TAG, "SHR: settingUserName: "+e.getMessage());
        }
    }

}
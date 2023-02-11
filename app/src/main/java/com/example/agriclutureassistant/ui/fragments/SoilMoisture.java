package com.example.agriclutureassistant.ui.fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.agriclutureassistant.R;


public class SoilMoisture extends Fragment {
BluetoothAdapter bluetoothAdapter;
TextView isconnect,percent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
 View view=inflater.inflate(R.layout.fragment_soil_moisture, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        isconnect=view.findViewById(R.id.isconnect);
        percent=view.findViewById(R.id.percentage);
        if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE), 1);
        }
        else {
            isconnect.setText("Error in Connection");
            percent.setText("Error");
        }
        }
}
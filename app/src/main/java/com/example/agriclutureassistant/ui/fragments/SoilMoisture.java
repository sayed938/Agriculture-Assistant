package com.example.agriclutureassistant.ui.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriclutureassistant.R;
import com.google.common.base.CharMatcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class SoilMoisture extends Fragment {
    BluetoothAdapter bluetoothAdapter;
    TextView isconnect, percent;
    OutputStream outputStream;
    InputStream inputStream = null;
    private Button booking_btn, open_water, close_water, reload_bt;
    private BluetoothSocket socket = null;
    static final java.util.UUID UUID = java.util.UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soil_moisture, container, false);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        isconnect = view.findViewById(R.id.isconnect);
        open_water = view.findViewById(R.id.open_w);
        close_water = view.findViewById(R.id.close_w);
        percent = view.findViewById(R.id.percentage);
        reload_bt = view.findViewById(R.id.bt_reload);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NavController navController = Navigation.findNavController(view);
        super.onViewCreated(view, savedInstanceState);

       /* if (ActivityCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {

                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE), 1);
        }*/

        try {
            defineBluetooth();
            displayHumidity();
        } catch (Exception e) {
            isconnect.setText("Check Connection !");
        }
        reload_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    displayHumidity();
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "check connection please", Toast.LENGTH_SHORT).show();
                }
            }
        });
        open_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openWater();
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "check connection please", Toast.LENGTH_SHORT).show();
                }
            }
        });
        close_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                   closeWater();
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "check connection please", Toast.LENGTH_SHORT).show();
                }

            }
        });

        booking_btn = view.findViewById(R.id.book_component_btn);
        booking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_hard_menu_to_bookingComponent);
            }
        });
    }

    private void defineBluetooth() {
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice("00:22:04:00:24:7D");
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            isconnect.setText("Bluetooth Connected");
        }
        int cnt = 0;
        do {
            try {
                socket = device.createRfcommSocketToServiceRecord(UUID);
                socket.connect();
                System.out.println("Connection   :   " + socket.isConnected());
            } catch (IOException e) {
                System.out.println("InError : " + e.getMessage());
            }
            cnt++;
        } while (!socket.isConnected() && cnt < 3);
    }

    private void displayHumidity() {
        try {
            outputStream = socket.getOutputStream();
            outputStream.write(105);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream = socket.getInputStream();
            inputStream.skip(inputStream.available());
            byte b = (byte) inputStream.read();
            percent.setText(b + "%");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openWater() {
        try {
            outputStream = socket.getOutputStream();
            outputStream.write(110);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWater() {
        try {
            outputStream = socket.getOutputStream();
            outputStream.write(107);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void closeBluetooth() {
        BluetoothSocket socket = null;
        defineBluetooth();
        try {
            socket.close();
            isconnect.setText("" + socket.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.example.drive_and_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

import DrivingSensors.DrivingSensors;

public class SamplingOnly extends AppCompatActivity {

    TextView longitude, latitude, speed, accuracy, address;
    DrivingSensors drivingSensors = new DrivingSensors();
    LocationRequest locationRequest;
    LocationRequest.Builder builder;
    FusedLocationProviderClient fusedLocationProviderClient;
    Switch sw;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampling_only);
        longitude = findViewById(R.id.longitude2);
        latitude = findViewById(R.id.latitude2);
        speed = findViewById(R.id.speed2);
        accuracy = findViewById(R.id.accuracy2);
        address = findViewById(R.id.address2);
        sw = findViewById(R.id.switch1);


        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw.isChecked())
                {
//                    drivingSensors.sensorsData(0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        builder = new LocationRequest.Builder(30000)
                                .setMinUpdateIntervalMillis(1000)
                                .setIntervalMillis(5000).setQuality(LocationRequest.QUALITY_BALANCED_POWER_ACCURACY);
                    }
                }
                else
                {
//                    drivingSensors.sensorsData(1);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        builder = new LocationRequest.Builder(30000)
                                .setMinUpdateIntervalMillis(1000)
                                .setIntervalMillis(5000).setQuality(LocationRequest.QUALITY_BALANCED_POWER_ACCURACY);
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    locationRequest = builder.build();
                }
                updateGPS();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 99:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    drivingSensors.updateGPS(this);
                }
        }
    }

    public void updateGPS()
    {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    updateUIValues(location);
                }
            });
        }
        else
        {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},99);
        }
    }

    private void updateUIValues(Location location)
    {
        longitude.setText(Double.toString(location.getLongitude()));
        latitude.setText(Double.toString(location.getLatitude()));
        speed.setText(Double.toString(location.getSpeed()) + " km/h");
        accuracy.setText(Double.toString(location.getAccuracy()));
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            address.setText(addressList.get(0).getAddressLine(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        address.setText();
    }

}
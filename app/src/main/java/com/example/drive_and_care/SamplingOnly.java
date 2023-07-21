package com.example.drive_and_care;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class SamplingOnly extends AppCompatActivity {

    TextView tv_lat, tv_lon, tv_altitude, tv_accuracy, tv_speed, tv_sensor, tv_updates, tv_address,textView;
    Switch sw_locationupdates, sw_gps;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationRequest.Builder builder;
    long speed_score = 0;
    LocationCallback locationCallBack;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_lat = findViewById(R.id.latitude2);
        tv_lon = findViewById(R.id.longitude2);
//        tv_altitude = findViewById(R.id.);
        tv_accuracy = findViewById(R.id.accuracy2);
        tv_speed = findViewById(R.id.speed2);
        tv_address = findViewById(R.id.address2);
        sw_gps = findViewById(R.id.switch1);
        textView = findViewById(R.id.textView);

        builder = new LocationRequest.Builder(30000)
                .setMinUpdateIntervalMillis(5000)
                .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);

        locationRequest = builder.build();

        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }

            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                updateUIValues(location);

            }
        };

        sw_gps.setOnClickListener(view -> {
            if (sw_gps.isChecked()) {
                builder = new LocationRequest.Builder(30000)
                        .setMinUpdateIntervalMillis(5000)
                        .setPriority(Priority.PRIORITY_HIGH_ACCURACY);
                locationRequest = builder.build();
                tv_sensor.setText("Using GPS sensors");
            } else {
                builder = new LocationRequest.Builder(30000)
                        .setMinUpdateIntervalMillis(5000)
                        .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);
                locationRequest = builder.build();
                tv_sensor.setText("Using Towers + WIFI");
            }
        });

        sw_locationupdates.setOnClickListener(view -> {
            if (sw_locationupdates.isChecked()) {
                startLocationUpdates();
            } else {
                stopLocationUpdates();
            }
        });
        updateGPS();
    }


}
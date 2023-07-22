package com.example.drive_and_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ScoringSystem.DrivingScoring;

public class HomeScreen extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    long drivingScore = 0;
    long REWARDS = 0;
    LocationRequest locationRequest;
    double distance = 0.0;
    LocationRequest.Builder builder;
    LocationCallback locationCallBack;
    DrivingScoring drivingScoring = new DrivingScoring();
    TextView textView7, textView9, textView_9;
    final Handler handler = new Handler();
    final long[] count = {0};
    Button button;
    Switch aSwitch;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        aSwitch = findViewById(R.id.switch2);
        textView7 = findViewById(R.id.textView7);
        textView9 = findViewById(R.id.textView9);
        textView_9 = findViewById(R.id.textView_9);

        builder = new LocationRequest.Builder(30000)
                .setMinUpdateIntervalMillis(5000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY);

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

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new LocationRequest.Builder(30000)
                        .setMinUpdateIntervalMillis(5000)
                        .setPriority(Priority.PRIORITY_HIGH_ACCURACY);
//
                locationRequest = builder.build();
                if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
                updateGPS();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        count[0]++;
                        handler.postDelayed(this,1000);
                    }
                });

            }


        });
        updateGPS();
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case 99:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    updateGPS();
                }
                else
                {
                    Toast.makeText(this, "This app requires.",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    private void updateGPS(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    updateUIValues(location);
                }
            });
        }
        else
        {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},99);
        }
    }
    public void updateUIValues(Location location){

        if(location.hasSpeed())
        {
            if(location.getSpeed()<3.0)
            {
                distance += count[0] * (5.0/18.0)* location.getSpeed();
            }
            drivingScore = drivingScoring.totalScore(location.getSpeed()*18/5,0.0, 0.0, (int)(Math.floor(Math.random()*2)), (int)(Math.floor(Math.random()*3)), distance);

            textView7.setText(String.valueOf(drivingScoring.overallScore));
            textView9.setText(((int)(location.getSpeed()*18/5)) + " km/h");
//            Toast.makeText(this, String.valueOf((int)location.getSpeed()), Toast.LENGTH_SHORT).show();
            textView_9.setText(String.valueOf(drivingScoring.REWARDS));
            if(drivingScore > 10000)
            {
                drivingScore -= 10000;
                REWARDS++;
            }
        }
        Geocoder geocoder = new Geocoder(this);
        try{
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
        }catch (Exception e)
        {
//            tv_address.setText("Unable to get street address");
        }
    }
}
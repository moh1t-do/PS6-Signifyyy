package DrivingSensors;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

public class DrivingSensors {
    LocationRequest locationRequest;
    LocationRequest.Builder builder;
    FusedLocationProviderClient fusedLocationProviderClient;
    public void sensorsData(int type)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if(type == 0)
            {
                //This is with mobile networks
                builder = new LocationRequest.Builder(30000)
                        .setMinUpdateIntervalMillis(1000)
                        .setIntervalMillis(5000).setQuality(LocationRequest.QUALITY_BALANCED_POWER_ACCURACY);
            }
            else
            {
                //This is with gps
                builder = new LocationRequest.Builder(30000)
                        .setMinUpdateIntervalMillis(1000)
                        .setIntervalMillis(5000).setQuality(LocationRequest.QUALITY_HIGH_ACCURACY);
            }
            locationRequest = builder.build();
        }
    }
    public void updateGPS(Context context)
    {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                }
            });
        }
    }





}

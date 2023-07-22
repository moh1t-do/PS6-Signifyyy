package com.example.drive_and_care;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.tasks.Task;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class FitnessScore extends AppCompatActivity {

    GoogleSignInClient signInClient;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_score);
        button = findViewById(R.id.button);
        // Build a GoogleSignInClient to request the Fit API
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 20)
//        {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//
//            }catch (Exception e)
//            {
//
//            }
//        }
//    }
    private void googleSignIn()
    {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(Fitness.SCOPE_ACTIVITY_READ,Fitness.SCOPE_NUTRITION_READ,Fitness.SCOPE_BODY_READ)
                .build();
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, options);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent,20);

        Fitness.getHistoryClient(this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(this)))
                .readData(new DataReadRequest.Builder()
                        .read(DataType.TYPE_STEP_COUNT_DELTA)
                        .setTimeRange(5, 50, TimeUnit.HOURS)
                        .build())
                .addOnSuccessListener(dataReadResponse -> {
                    // Process the dataReadResponse
                    // Data will be available in dataReadResponse.getDataSet(DataType.TYPE_STEP_COUNT_DELTA)
                    Log.i("Kuch", String.valueOf(dataReadResponse.getDataSet(DataType.TYPE_STEP_COUNT_DELTA)));
                })
                .addOnFailureListener(e -> {
                    // Handle the failure
                });
    }


}
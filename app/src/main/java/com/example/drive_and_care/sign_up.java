package com.example.drive_and_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class sign_up extends AppCompatActivity {
    public static final String TAG = "mtag";
    FloatingActionButton floatingActionButton;

    EditText editTextTextPersonName, editTextTextEmailAddress, editTextPhone,
            city, editTextTextPassword;

    FirebaseAuth auth;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();

        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity1();
            }
        });

        // signUpButton
        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
                editTextTextPassword = findViewById(R.id.editTextTextPassword);
                String userEmail, userPassword, userName, userPhone, userCity;
                userEmail = editTextTextEmailAddress.getText().toString();
                userPassword = editTextTextPassword.getText().toString();
                userName = editTextTextPersonName.getText().toString();
                userPhone = editTextPhone.getText().toString();
                userCity = city.getText().toString();

                signUpEmail(userEmail, userPassword);
            }
        });
    }


    public void openActivity1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void signUpEmail(String email, String password) {
        // register user with email and password
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: Register user with email sucessfull!");

                            // SQLite code:

                        }else{
                            Log.d(TAG, "onComplete: Register user with email failed!");
                        }
                    }
                });
    }

}
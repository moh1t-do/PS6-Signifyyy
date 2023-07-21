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

    EditText name, email, phone, city, password;

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
                email = findViewById(R.id.userEmail);
                password = findViewById(R.id.userPassword);
                name = findViewById(R.id.userName);
                phone = findViewById(R.id.userPhone);
                city = findViewById(R.id.userCity);

                Log.d(TAG, "onClick: SignUp clicked");
                signUpEmail(email.getText().toString(), password.getText().toString(),
                name.getText().toString(), phone.getText().toString(),
                        city.getText().toString());
            }
        });
    }


    public void openActivity1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void signUpEmail(String email, String password, String name, String phone, String city) {


        // register user with email and password
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: Register user with email sucessfull!");

                            // SQLite code:
                            DatabaseHelper db = new DatabaseHelper(sign_up.this);
                            db.addUser(name, city, phone);

                        }else{
                            Log.d(TAG, "onComplete: Register user with email failed!");
                        }
                    }
                });
    }

}
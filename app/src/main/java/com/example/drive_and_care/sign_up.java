package com.example.drive_and_care;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class sign_up extends AppCompatActivity {
    public static final String TAG = "mtag";
    FloatingActionButton floatingActionButton;

    EditText name, email, phone, city, password;

    FirebaseAuth auth;
    Button signUpButton;
    CardView googleSignUpButton;

    GoogleSignInClient googleSignInClient;
    int RC_SIGN_IN = 20;

    Spinner gender, vehicle;

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

        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);
        name = findViewById(R.id.userName);
        phone = findViewById(R.id.userPhone);
        city = findViewById(R.id.userCity);
        gender = findViewById(R.id.spinner_dropdown);
        vehicle = findViewById(R.id.spinner_Vehicle_type);


        auth =FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: SignUp clicked");
//                Log.d(TAG, "onClick: " + gender.getSelectedItem().toString());
                signUpEmail(email.getText().toString(), password.getText().toString(),
                name.getText().toString(), phone.getText().toString(),
                        city.getText().toString(), gender.getSelectedItem().toString(),
                        vehicle.getSelectedItem().toString());
            }
        });

        googleSignUpButton = findViewById(R.id.googleSignUp);
        googleSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: signup google");
                googleSignIn();
            }
        });
    }


    public void openActivity1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void signUpEmail(String email, String password, String name, String phone, String city,
                             String gender, String vehicle) {


        // register user with email and password
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "FB: Register user with email successful!");

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            // SQLite code:
                            DatabaseHelper db = new DatabaseHelper(sign_up.this);
                            db.addUser(user.getUid(), name, city, phone, gender, vehicle);

                        }else{
                            Log.d(TAG, "FB: Register user with email failed!");
                        }
                    }
                });
    }

    private void googleSignIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());

            }catch (Exception e){
                Log.d(TAG, "FB : " + e.getMessage());
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            // SQLite code:
                            DatabaseHelper db = new DatabaseHelper(sign_up.this);
                            db.addUser(user.getUid(), user.getDisplayName(), city.getText().toString(), phone.getText().toString(),
                                    gender.getSelectedItem().toString(), vehicle.getSelectedItem().toString());
                            Log.d(TAG, "FB: User registered");
                        }
                        else{
                            Log.d(TAG, "FB: User register error");
                        }
                    }
                });
    }

}
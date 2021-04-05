package com.example.kashaf.ocm.fyp.Activities.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.kashaf.ocm.fyp.Activities.network.SharedPrefManager;
import com.example.kashaf.ocm.fyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    int pref;
    EditText edtEmail, edtpassword;
    TextView tvLinkSignUp;
    private FirebaseAuth mAuth;
    Button btnLogin;
    String getEmail, getPassword, key;
    ProgressDialog progressDialog;
    AwesomeValidation awesomeValidation;
    SharedPrefManager sharedPrefManager = new SharedPrefManager();
    private DatabaseReference userAccountRef, mechanicAccountRef;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtpassword = findViewById(R.id.edtPassword);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        btnLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();
        //userAccountRef = FirebaseDatabase.getInstance().getReference("User");
        //mechanicAccountRef = FirebaseDatabase.getInstance().getReference("Mechanic");

        pref = getIntent().getIntExtra("pref", 0);

        if (pref==3){
            tvLinkSignUp.setVisibility(View.GONE);
        }

        //For Email
        awesomeValidation.addValidation(this, R.id.edtEmail, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        //For Password
        awesomeValidation.addValidation(this, R.id.edtPassword, ".{6,}", R.string.invalid_password);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getEmail = edtEmail.getText().toString();
                getPassword = edtpassword.getText().toString();

                if (pref == 1) {

                    if (awesomeValidation.validate()) {
                        userLogin();
                    } else {
                        Toast.makeText(LoginActivity.this, "One or more fields are invalid!", Toast.LENGTH_SHORT).show();
                    }

                } else if (pref == 2) {
                    if (awesomeValidation.validate()) {
                        mechanicLogin();
                    } else {
                        Toast.makeText(LoginActivity.this, "One or more fields are invalid!", Toast.LENGTH_SHORT).show();
                    }
                } else if (pref == 3) {
                    if (awesomeValidation.validate()) {
                        adminLogin();
                    } else {
                        Toast.makeText(LoginActivity.this, "One or more fields are invalid!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        tvLinkSignUp = findViewById(R.id.tvLinkSignup);
        tvLinkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pref == 1) {
                    Intent user = new Intent(getApplicationContext(), UserSignUpActivity.class);
                    user.putExtra("pref", 1);
                    startActivity(user);
                    finish();
                } else if (pref == 2) {
                    Intent user = new Intent(getApplicationContext(), MechanicSignUpActivity.class);
                    user.putExtra("pref", 2);
                    startActivity(user);
                    finish();
                }
            }
        });

    }

    private void userLogin() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Signing in account please wait!");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressDialog.dismiss();
                            firebaseUser = mAuth.getCurrentUser();

                            if (firebaseUser != null) {
                                key = firebaseUser.getUid();
                            }

                            userAccountRef = FirebaseDatabase.getInstance().getReference("User").child(key);
                            userAccountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    String _name = dataSnapshot.child("name").getValue().toString();
                                    String _email = dataSnapshot.child("email").getValue().toString();
                                    String _profileURL = dataSnapshot.child("profile_url").getValue().toString();
                                    String _phone = dataSnapshot.child("phoneNo").getValue().toString();
                                    sharedPrefManager.saveUserData(LoginActivity.this
                                            , key,
                                            _name,
                                            _email,
                                            _phone

                                    );
                                    startActivity(new Intent(getApplicationContext(), UserDashboardActivity.class));
                                    finish();
                                    sharedPrefManager.loginPreference(LoginActivity.this, 1);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                    sharedPrefManager.loginPreference(LoginActivity.this, 0);
                                }
                            });


                        } else {

                            sharedPrefManager.loginPreference(LoginActivity.this, 0);
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Email or password is invalid", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "wrong Credentials!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mechanicLogin() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Signing in please wait!");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressDialog.dismiss();
                            firebaseUser = mAuth.getCurrentUser();


                            if (firebaseUser != null) {
                                key = firebaseUser.getUid();
                            }

                            mechanicAccountRef = FirebaseDatabase.getInstance().getReference("Mechanic").child(key);
                            mechanicAccountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    String _name = dataSnapshot.child("name").getValue().toString();
                                    String _email = dataSnapshot.child("email").getValue().toString();
                                    String _profileURL = dataSnapshot.child("profile_url").getValue().toString();
                                    String _phone = dataSnapshot.child("phoneNo").getValue().toString();
                                    String _cnic = dataSnapshot.child("cnic").getValue().toString();
                                    String _type = dataSnapshot.child("type").getValue().toString();
                                    sharedPrefManager.saveMechanicData(LoginActivity.this
                                            , key,
                                            _name,
                                            _email,
                                            _phone,
                                            _cnic,
                                            _type

                                    );
                                    startActivity(new Intent(getApplicationContext(), MechanicDashboard.class));
                                    finish();
                                    sharedPrefManager.loginPreference(LoginActivity.this, 2);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                    sharedPrefManager.loginPreference(LoginActivity.this, 0);
                                }
                            });


                        } else {

                            sharedPrefManager.loginPreference(LoginActivity.this, 0);
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Email or password is invalid", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "wrong Credentials!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adminLogin() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Creating account please wait!");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressDialog.dismiss();
                            firebaseUser = mAuth.getCurrentUser();


                            if (firebaseUser != null) {
                                key = firebaseUser.getUid();
                            }

                            startActivity(new Intent(getApplicationContext(), AdminDashboardActivity.class));
                            finish();
                            sharedPrefManager.loginPreference(LoginActivity.this, 3);


                        } else {

                            sharedPrefManager.loginPreference(LoginActivity.this, 0);
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Email or password is invalid", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "wrong Credentials!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

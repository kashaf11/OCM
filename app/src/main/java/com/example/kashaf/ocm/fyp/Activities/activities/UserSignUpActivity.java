package com.example.kashaf.ocm.fyp.Activities.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.kashaf.ocm.fyp.Activities.network.NetworkManager;
import com.example.kashaf.ocm.fyp.Activities.network.SharedPrefManager;
import com.example.kashaf.ocm.fyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSignUpActivity extends AppCompatActivity {
    EditText UserEmail, UserPassword, UserFullName, UserMobileNo;
    Button btnSignUp;
    int pref = -1;
    String getName, getEmail, getPassword, getPhoneNo, key, token;
    private FirebaseAuth mAuth;
    private DatabaseReference userAccountDetailsRef;
    private FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    private SharedPrefManager sharedPrefManager = new SharedPrefManager();
    private NetworkManager manager = new NetworkManager();
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);

        mAuth = FirebaseAuth.getInstance();
        userAccountDetailsRef = FirebaseDatabase.getInstance().getReference("User");

        //Assign variable
        UserEmail = findViewById(R.id.UserEmail);
        UserPassword = findViewById(R.id.UserPassword);
        UserFullName = findViewById(R.id.FullName);
        UserMobileNo = findViewById(R.id.UserMobileNo);
        btnSignUp = findViewById(R.id.btnSignUp);

        pref = getIntent().getIntExtra("pref", 0);
        //initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //For MobileNo
        awesomeValidation.addValidation(this, R.id.UserMobileNo, "^[+]?[0-9]{11}$", R.string.invalid_mobile);

        //For Email
        awesomeValidation.addValidation(this, R.id.UserEmail, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        //For Password
        awesomeValidation.addValidation(this, R.id.UserPassword, ".{6,}", R.string.invalid_password);

        //For FullName
        awesomeValidation.addValidation(this, R.id.FullName, RegexTemplate.NOT_EMPTY, R.string.invalid_fullname);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check validation

                getName = UserFullName.getText().toString();
                getEmail = UserEmail.getText().toString();
                getPassword = UserPassword.getText().toString();
                getPhoneNo = UserMobileNo.getText().toString();

                if (awesomeValidation.validate()) {

                    createUserAccount();

                } else {
                    Toast.makeText(getApplicationContext(), "Validation Faild", Toast.LENGTH_SHORT).show();
                }


            }

        });

    }

    private void createUserAccount() {

        progressDialog = new ProgressDialog(UserSignUpActivity.this);
        progressDialog.setTitle("Creating account please wait!");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            firebaseUser = mAuth.getCurrentUser();


                            if (firebaseUser != null) {
                                key = firebaseUser.getUid();

                                userAccountDetailsRef.child(key).child("name").setValue(getName);
                                userAccountDetailsRef.child(key).child("email").setValue(getEmail);
                                userAccountDetailsRef.child(key).child("phoneNo").setValue(getPhoneNo);
                                userAccountDetailsRef.child(key).child("status").setValue("accepted");
                                userAccountDetailsRef.child(key).child("profile_url").setValue("none");

                                //On success
                                sharedPrefManager.saveUserData(UserSignUpActivity.this,
                                        key,
                                        getName,
                                        getEmail,
                                        getPhoneNo);
                                sharedPrefManager.loginPreference(UserSignUpActivity.this, 1);
                                startActivity(new Intent(getApplicationContext(), UserDashboardActivity.class));
                                finish();
                                Toast.makeText(getApplicationContext(), "Account Created Successfully...", Toast.LENGTH_SHORT).show();


                            }


                            //progressDialog.dismiss();
                            //Toast.makeText(SignupActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                        } else {
                            sharedPrefManager.loginPreference(UserSignUpActivity.this, 0);
                            progressDialog.dismiss();
                            Toast.makeText(UserSignUpActivity.this, "Email already exists choose another!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (pref == 1) {
            Intent user = new Intent(getApplicationContext(), LoginActivity.class);
            user.putExtra("pref", 1);
            startActivity(user);
            finish();
        } else if (pref == 2) {
            Intent user = new Intent(getApplicationContext(), LoginActivity.class);
            user.putExtra("pref", 1);
            startActivity(user);
            finish();
        }
    }
}
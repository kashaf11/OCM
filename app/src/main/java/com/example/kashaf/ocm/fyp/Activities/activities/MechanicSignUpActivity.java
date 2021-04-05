package com.example.kashaf.ocm.fyp.Activities.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

public class MechanicSignUpActivity extends AppCompatActivity {

    Spinner spinner_pref;
    String getName, getEmail, getPassword, getCnic, getPhoneNo, key, getType;
    private FirebaseAuth mAuth;
    private DatabaseReference userAccountDetailsRef;
    private FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    private SharedPrefManager sharedPrefManager = new SharedPrefManager();
    private NetworkManager manager = new NetworkManager();
    String pref_array[] = {"Select Mechanic Type", "Self Employee", "Workshop"};


    EditText edtFullName, edtEmail, edtPassword, edtCnic, edtPhoneNo;
    Button btnSignUp;
    String fullName, email, password, cnic, phoneNo;
    boolean isPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_mechanic);

        mAuth = FirebaseAuth.getInstance();
        userAccountDetailsRef = FirebaseDatabase.getInstance().getReference("Mechanic");

        AwesomeValidation awesomeValidation;
        edtFullName = findViewById(R.id.FullName);
        edtEmail = findViewById(R.id.MechanicEmail);
        edtPassword = findViewById(R.id.MechanicPassword);
        edtCnic = findViewById(R.id.MechanicCNIC);
        edtPhoneNo = findViewById(R.id.MechanicMobileNo);
        btnSignUp = findViewById(R.id.btnSignUp);
        spinner_pref = findViewById(R.id.spinner_perf);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pref_array);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner_pref.setAdapter(spinnerArrayAdapter);
        spinner_pref.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    isPref = false;
                } else {
                    isPref = true;
                    getType = spinner_pref.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //For MobileNo
        awesomeValidation.addValidation(this, R.id.MechanicMobileNo, "^[+]?[0-9]{11}$", R.string.invalid_mobile);

        //For Cnic
        awesomeValidation.addValidation(this, R.id.MechanicCNIC, "^[+]?[0-9]{13}$", R.string.invalid_mobile);


        //For Email
        awesomeValidation.addValidation(this, R.id.MechanicEmail, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        //For Password
        awesomeValidation.addValidation(this, R.id.MechanicPassword, ".{6,20}", R.string.invalid_password);

        //For FullName
        awesomeValidation.addValidation(this, R.id.FullName, RegexTemplate.NOT_EMPTY, R.string.invalid_fullname);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (awesomeValidation.validate() && isPref) {
                    getName = edtFullName.getText().toString();
                    getEmail = edtEmail.getText().toString();
                    getPassword = edtPassword.getText().toString();
                    getPhoneNo = edtPhoneNo.getText().toString();
                    getCnic = edtCnic.getText().toString();
                    createMechanicAccount();
                } else {
                    Toast.makeText(getApplicationContext(), "Validation Faild", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void createMechanicAccount() {

        progressDialog = new ProgressDialog(MechanicSignUpActivity.this);
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
                                userAccountDetailsRef.child(key).child("cnic").setValue(getCnic);
                                userAccountDetailsRef.child(key).child("type").setValue(getType);
                                userAccountDetailsRef.child(key).child("status").setValue("pending");
                                userAccountDetailsRef.child(key).child("profile_url").setValue("none");

                                //On success
                                sharedPrefManager.saveMechanicData(MechanicSignUpActivity.this,
                                        key,
                                        getName,
                                        getEmail,
                                        getPhoneNo,
                                        getCnic,
                                        getType);
                                sharedPrefManager.loginPreference(MechanicSignUpActivity.this, 2);
                                // startActivity(new Intent(getApplicationContext(), MechanicDash.class));
                                Toast.makeText(getApplicationContext(), "Account Created Successfully...", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MechanicDashboard.class));
                                finish();


                            }


                            //progressDialog.dismiss();
                            //Toast.makeText(SignupActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                        } else {
                            sharedPrefManager.loginPreference(MechanicSignUpActivity.this, 0);
                            progressDialog.dismiss();
                            Toast.makeText(MechanicSignUpActivity.this, "Email already exists choose another!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent user = new Intent(getApplicationContext(), LoginActivity.class);
        user.putExtra("pref", 2);
        startActivity(user);
        finish();
    }
}

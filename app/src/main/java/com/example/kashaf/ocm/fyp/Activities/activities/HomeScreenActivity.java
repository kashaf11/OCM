package com.example.kashaf.ocm.fyp.Activities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.kashaf.ocm.fyp.R;

public class HomeScreenActivity extends
        AppCompatActivity {

    CardView cardMechanic, cardUser, cardAdmin;
    int perf = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardMechanic = findViewById(R.id.cardMechanic);
        cardUser = findViewById(R.id.cardUser);
        cardAdmin = findViewById(R.id.cardAdmin);

        cardUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent user = new Intent(getApplicationContext(), LoginActivity.class);
                user.putExtra("pref", 1);
                startActivity(user);

            }
        });

        cardMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mechanic = new Intent(getApplicationContext(), LoginActivity.class);
                mechanic.putExtra("pref", 2);
                startActivity(mechanic);

            }
        });

        cardAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent admin = new Intent(getApplicationContext(), LoginActivity.class);
                admin.putExtra("pref", 3);
                startActivity(admin);

            }
        });
    }
}

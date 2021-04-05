package com.example.kashaf.ocm.fyp.Activities.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.kashaf.ocm.fyp.Activities.network.SharedPrefManager;
import com.example.kashaf.ocm.fyp.R;

public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    int loginPref = 0;
    SharedPrefManager sharedPrefManager = new SharedPrefManager();

    //SharedPrefManager manager = new SharedPrefManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);
        Animation top_animation;

        loginPref = sharedPrefManager.getLoginPreference(SplashScreenActivity.this);

        TextView image = (TextView) findViewById(R.id.textView3);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_animation);
        image.startAnimation(animation1);

        // loginPref = manager.getLoginPreference(this);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                if (loginPref == 1) {
                    Intent i = new Intent(SplashScreenActivity.this, UserDashboardActivity.class);
                    startActivity(i);
                    finish();
                } else if (loginPref == 2) {
                    Intent i = new Intent(SplashScreenActivity.this, MechanicSignUpActivity.class);
                    startActivity(i);
                    finish();
                } else if (loginPref == 3) {
                    Intent i = new Intent(SplashScreenActivity.this, AdminDashboardActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashScreenActivity.this, HomeScreenActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }

    public void slide(View z) {
        TextView image = (TextView) findViewById(R.id.textView3);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_animation);
        image.startAnimation(animation1);
    }
}

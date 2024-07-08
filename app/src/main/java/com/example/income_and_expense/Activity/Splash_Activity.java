package com.example.income_and_expense.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.income_and_expense.R;

public class Splash_Activity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000; // Splash screen delay time in milliseconds

    ImageView splash_background;

    // Dark mode
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_MODE = "dark_mode";

    boolean isDarkMode;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Dark-Light Mode
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean(PREF_DARK_MODE, false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_splash);

        splash_background = findViewById(R.id.spalsh_background);

        if (isDarkMode) {
            splash_background.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.dark_splash_bg));
        } else {
            splash_background.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.splashscreen_background));
        }

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Retrieve the shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                String userEmail = sharedPreferences.getString("USER_EMAIL", null);

                // Determine the next activity based on whether the user is logged in or not
                Intent intent;
                if (userEmail != null) {
                    intent = new Intent(Splash_Activity.this, Home_Activity.class);
                    intent.putExtra("USER_EMAIL", userEmail);
                } else {
                    intent = new Intent(Splash_Activity.this, Login_Activity.class);
                }

                // Start the appropriate activity and finish the splash screen activity
                startActivity(intent);
                finish();
            }
        };

        handler.postDelayed(runnable, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
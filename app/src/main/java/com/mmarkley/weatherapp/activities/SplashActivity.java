package com.mmarkley.weatherapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mmarkley.weatherapp.datamodel.WeatherModel;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeatherModel.init(getApplicationContext());

        // Start home activity
        startActivity(new Intent(SplashActivity.this, MapsActivity.class));

        // close splash activity
        finish();
    }
}

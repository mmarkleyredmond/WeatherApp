package com.mmarkley.weatherapp.settings;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

public class Settings {
    private static final String PREFERENCES_NAME = "WeatherApp";
    private static final String LATITUDE = "LATITUDE";
    private static final String LONGITUDE = "LONGITUDE";
    private static final String LOCATION_NAME = "LOCATION_NAME";

    public static void saveLocation(Context context,  LatLng latLng) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        if(null != sharedPreferences) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(LATITUDE, String.valueOf(latLng.latitude));
            editor.putString(LONGITUDE, String.valueOf(latLng.longitude));
            editor.apply();
        }
    }

    public static void saveLocationName(Context context, @NonNull String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        if(null != sharedPreferences) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(LOCATION_NAME, name);
            editor.apply();
        }

    }

    public static LatLng getSavedLocation(Context context) {
        LatLng latLng = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        if(null != sharedPreferences) {
            String latitude = sharedPreferences.getString(LATITUDE, "47.6062");
            if(null == latitude) {
                latitude = "47.6062";
            }
            String longitude = sharedPreferences.getString(LONGITUDE, "-122.3321");
            if(null == longitude) {
                longitude = "-122.3321";
            }
            latLng = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
        }
        return latLng;
    }

    public static String getSavedLocationName(Context context) {
        String locationName = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        if(null != sharedPreferences) {
            locationName = sharedPreferences.getString(LOCATION_NAME, null);
            if (null == locationName) {
                locationName = "Seattle";
            }
        }
        return locationName;
    }
}

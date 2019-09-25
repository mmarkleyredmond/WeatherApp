package com.mmarkley.weatherapp.datamodel.interfaces;

public interface WeatherModelCallback {
    void onLocationSuccess();
    void onLocationFailure();
}

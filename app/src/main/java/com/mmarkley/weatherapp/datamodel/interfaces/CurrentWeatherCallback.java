package com.mmarkley.weatherapp.datamodel.interfaces;

import com.mmarkley.weatherapp.datamodel.weatherbit.CurrentWeather;

public interface CurrentWeatherCallback {
    void onSuccess(CurrentWeather currentWeather);
    void onFailure(Throwable throwable);
}

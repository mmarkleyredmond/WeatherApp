package com.mmarkley.weatherapp.datamodel.interfaces;

import com.mmarkley.weatherapp.datamodel.weatherbit.CurrentWeather;

public interface CurrentWeatherCallback {
    void onCurrentWeatherSuccess(CurrentWeather currentWeather);
    void onCurrentWeatherFailure(Throwable throwable);
}

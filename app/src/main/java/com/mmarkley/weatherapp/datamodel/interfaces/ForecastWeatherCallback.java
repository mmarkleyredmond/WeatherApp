package com.mmarkley.weatherapp.datamodel.interfaces;

import com.mmarkley.weatherapp.datamodel.weatherbit.ForecastWeather;

public interface ForecastWeatherCallback {
    void onSuccess(ForecastWeather forecastWeather);
    void onFailure(Throwable throwable);
}

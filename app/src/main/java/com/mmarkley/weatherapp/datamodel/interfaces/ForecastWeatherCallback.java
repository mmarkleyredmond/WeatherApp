package com.mmarkley.weatherapp.datamodel.interfaces;

import com.mmarkley.weatherapp.datamodel.weatherbit.ForecastWeather;

public interface ForecastWeatherCallback {
    void onExtendedForecastSuccess(ForecastWeather forecastWeather);
    void onExtendedForecastFailure(Throwable throwable);
}

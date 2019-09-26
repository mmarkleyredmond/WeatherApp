package com.mmarkley.weatherapp.datamodel.interfaces;

import com.mmarkley.weatherapp.datamodel.weatherbit.CurrentWeather;

public interface CurrentWeatherCallback {
    /**
     * Method invoked by the data model when the Current Weather has been retrieved
     * @param currentWeather A {@link CurrentWeather} object
     */
    void onCurrentWeatherSuccess(CurrentWeather currentWeather);

    /**
     * Method invoked by the data model when the Current Weather could not be retrieved
     * @param throwable A {@link Throwable} with details about the failure
     */
    void onCurrentWeatherFailure(Throwable throwable);
}

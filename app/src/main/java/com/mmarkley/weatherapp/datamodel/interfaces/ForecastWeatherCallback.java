package com.mmarkley.weatherapp.datamodel.interfaces;

import com.mmarkley.weatherapp.datamodel.weatherbit.ForecastWeather;

public interface ForecastWeatherCallback {
    /**
     * Method invoked by the {@link com.mmarkley.weatherapp.datamodel.WeatherBitModel} when
     * the Forecast information has been retrieved from the endpoint
     * @param forecastWeather A {@link ForecastWeather} object containing the long range forecast
     *                        data
     */
    void onExtendedForecastSuccess(ForecastWeather forecastWeather);

    /**
     * Method invoked by the {@link com.mmarkley.weatherapp.datamodel.WeatherBitModel} when
     * the attempt to retrieve information has failed
     * @param throwable A {@link Throwable} with details about the failure
     */
    void onExtendedForecastFailure(Throwable throwable);
}

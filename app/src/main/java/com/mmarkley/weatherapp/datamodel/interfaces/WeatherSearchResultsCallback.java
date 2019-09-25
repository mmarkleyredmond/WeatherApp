package com.mmarkley.weatherapp.datamodel.interfaces;

import com.mmarkley.weatherapp.datamodel.WeatherSearchResults;

public interface WeatherSearchResultsCallback {
    void onSearchSuccess(WeatherSearchResults results);
    void onSearchFailure(Throwable e);
}

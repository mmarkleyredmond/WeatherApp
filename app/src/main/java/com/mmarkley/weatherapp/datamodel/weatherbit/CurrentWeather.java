package com.mmarkley.weatherapp.datamodel.weatherbit;

import java.util.List;

public class CurrentWeather {
    private int count;

    private List<WeatherObservation> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<WeatherObservation> getData() {
        return data;
    }

    public void setData(List<WeatherObservation> data) {
        this.data = data;
    }
}

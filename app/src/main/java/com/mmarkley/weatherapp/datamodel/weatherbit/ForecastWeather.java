package com.mmarkley.weatherapp.datamodel.weatherbit;

import java.util.List;

public class ForecastWeather {
    private List<DailyForecastWeather> data;
    private String city_name;
    private String timezone;
    private double lon;
    private double lat;
    private String state_code;
    private String country_code;

    public List<DailyForecastWeather> getData() {
        return data;
    }

    public void setData(List<DailyForecastWeather> data) {
        this.data = data;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
}

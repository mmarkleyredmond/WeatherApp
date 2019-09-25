package com.mmarkley.weatherapp.datamodel;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationConsolidatedWeather {
    private long id;
    private String applicableDate;
    private String weatherStateName;

    private String weatherStateAbbr;
    private double wind_speed; //	mph
    private double wind_direction; //	degrees
    private String wind_direction_compass; // compass point	Compass point of the wind direction
    private double minTemperature; // Centigrade;
    private double maxTemperature; // Centigrade
    private double currentTemperature; // Centigrade
    private float airPressure;	// mbar
    private float humidity; // percentage
    private float visibility; // in miles
    private int predictability; //percentage	Our interpretation of the level to which the forecasters agree with each other - 100% being a complete consensus.

    public static LocationConsolidatedWeather fromJSONObject(@NonNull JSONObject jsonObject) {
        LocationConsolidatedWeather weather = new LocationConsolidatedWeather();
        try {
            if(jsonObject.has("id")) {
                weather.setId(jsonObject.getLong("id"));
            }
            if(jsonObject.has("applicable_date")) {
                weather.setApplicableDate(jsonObject.getString("applicable_date"));
            }
            if(jsonObject.has("wind_speed")) {
                weather.setWind_speed(jsonObject.getDouble("wind_speed"));
            }
            if(jsonObject.has("wind_direction")) {
                weather.setWind_direction(jsonObject.getDouble("wind_direction"));
            }
            if(jsonObject.has("min_temp")) {
                weather.setMinTemperature(jsonObject.getDouble("min_temp"));
            }
            if(jsonObject.has("max_temp")) {
                weather.setMaxTemperature(jsonObject.getDouble("max_temp"));
            }
            if(jsonObject.has("the_temp")) {
                weather.setCurrentTemperature(jsonObject.getDouble("the_temp"));
            }
            if(jsonObject.has("weather_state_name")) {
                weather.setWeatherStateName(jsonObject.getString("weather_state_name"));
            }

        } catch(Exception e) {
            Logger.getLogger(LocationConsolidatedWeather.class.getSimpleName()).log(Level.SEVERE, e.getMessage());
        }

        return weather;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
    }

    public String getWeatherStateName() {
        return weatherStateName;
    }

    public void setWeatherStateName(String weatherStateName) {
        this.weatherStateName = weatherStateName;
    }

    public String getWeatherStateAbbr() {
        return weatherStateAbbr;
    }

    public void setWeatherStateAbbr(String weatherStateAbbr) {
        this.weatherStateAbbr = weatherStateAbbr;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public double getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(double wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_direction_compass() {
        return wind_direction_compass;
    }

    public void setWind_direction_compass(String wind_direction_compass) {
        this.wind_direction_compass = wind_direction_compass;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public float getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(float airPressure) {
        this.airPressure = airPressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public int getPredictability() {
        return predictability;
    }

    public void setPredictability(int predictability) {
        this.predictability = predictability;
    }
}

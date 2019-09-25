package com.mmarkley.weatherapp.datamodel;

public class WeatherLattLong {

    private float latt;
    private float longitude;

    WeatherLattLong(float latt, float longitude) {
        this.latt = latt;
        this.longitude = longitude;
    }

    public float getLatt() {
        return latt;
    }

    void setLatt(float latt) {
        this.latt = latt;
    }

    public float getLongitude() {
        return longitude;
    }

    void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}

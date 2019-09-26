package com.mmarkley.weatherapp.datamodel.weatherbit;

public class DailyForecastWeather {
    String valid_date;
    EmbeddedWeather weather;
    double max_temp;
    double min_temp;
    double precip;

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public EmbeddedWeather getWeather() {
        return weather;
    }

    public void setWeather(EmbeddedWeather weather) {
        this.weather = weather;
    }

    public double getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(double max_temp) {
        this.max_temp = max_temp;
    }

    public double getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(double min_temp) {
        this.min_temp = min_temp;
    }

    public double getPrecip() {
        return precip;
    }

    public void setPrecip(double precip) {
        this.precip = precip;
    }
}

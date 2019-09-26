package com.mmarkley.weatherapp.datamodel.weatherbit;

/*
{
 "icon":"c03d",
 "code":"803",
 "description":"Broken clouds"
 */

public class EmbeddedWeather {
    private String icon;

    private String code;

    private String description;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

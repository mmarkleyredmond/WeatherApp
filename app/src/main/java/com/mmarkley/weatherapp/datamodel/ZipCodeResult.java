package com.mmarkley.weatherapp.datamodel;

public class ZipCodeResult {
    private ZipGeometry geometry;
    private String formatted_address;

    public ZipGeometry getGeometry() {
        return geometry;
    }

    public String getFormatted_address() {
        return formatted_address;
    }
}

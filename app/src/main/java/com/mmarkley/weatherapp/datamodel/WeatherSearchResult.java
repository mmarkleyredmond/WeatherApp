package com.mmarkley.weatherapp.datamodel;

import com.mmarkley.weatherapp.datamodel.types.SearchResultType;

public class WeatherSearchResult {
    private String title;

    private SearchResultType location_type;

    private WeatherLattLong lattLong;

    private Integer woeid;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SearchResultType getLocation_type() {
        return location_type;
    }

    public void setLocation_type(SearchResultType location_type) {
        this.location_type = location_type;
    }

    public WeatherLattLong getLattLong() {
        return lattLong;
    }

    public void setLattLong(WeatherLattLong lattLong) {
        this.lattLong = lattLong;
    }

    public Integer getWoeid() {
        return woeid;
    }

    public void setWoeid(Integer woeid) {
        this.woeid = woeid;
    }

}

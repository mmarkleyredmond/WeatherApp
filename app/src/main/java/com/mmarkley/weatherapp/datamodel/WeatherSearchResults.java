package com.mmarkley.weatherapp.datamodel;

import java.util.List;

public class WeatherSearchResults {
    private List<WeatherSearchResult> searchResults;

    public List<WeatherSearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<WeatherSearchResult> searchResults) {
        this.searchResults = searchResults;
    }

}

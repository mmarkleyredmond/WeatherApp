package com.mmarkley.weatherapp.datamodel;

import com.mmarkley.weatherapp.datamodel.types.SearchResultType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationWeather {
    private List<LocationConsolidatedWeather> consolidatedWeather;

    private List<LocationWeatherSource> dataSources;

    private String title;


    private SearchResultType location_type;

    private WeatherLattLong lattLong;

    private Integer woeid;

    static LocationWeather fromJsonObject(JSONObject jsonObject) {
        LocationWeather weather = new LocationWeather();
        weather.dataSources = new ArrayList<>();
        weather.consolidatedWeather = new ArrayList<>();
        try {
            if (jsonObject.has("sources")) {
                JSONArray jsonSources = jsonObject.getJSONArray("sources");
                for (int i = 0; i < jsonSources.length(); i++) {
                    JSONObject sourceObj = jsonSources.getJSONObject(i);
                    LocationWeatherSource source = LocationWeatherSource.fromJSONObject(sourceObj);
                    if(null != source) {
                        weather.dataSources.add(source);
                    }

                }
            }
            if(jsonObject.has("consolidated_weather")) {
                JSONArray consolidatedWeatherArray = jsonObject.getJSONArray("consolidated_weather");
                for(int i = 0; i < consolidatedWeatherArray.length(); i++) {
                    JSONObject weatherEntry = consolidatedWeatherArray.getJSONObject(i);
                    LocationConsolidatedWeather consolidatedWeather = LocationConsolidatedWeather.fromJSONObject(weatherEntry);
                    weather.consolidatedWeather.add(consolidatedWeather);
                }
            }
            if(jsonObject.has("location_type")) {
                weather.location_type = SearchResultType.fromString(jsonObject.getString("location_type"));
            }
            if(jsonObject.has("woeid")) {
                weather.woeid = jsonObject.getInt("woeid");
            }
        } catch(Exception ignored) {
            Logger.getLogger(LocationWeather.class.getSimpleName()).log(Level.SEVERE, "Error parsing " + jsonObject.toString());
            weather = null;
        }

        return weather;
    }

    public List<LocationConsolidatedWeather> getConsolidatedWeather() {
        return consolidatedWeather;
    }

    public List<LocationWeatherSource> getDataSources() {
        return dataSources;
    }
}

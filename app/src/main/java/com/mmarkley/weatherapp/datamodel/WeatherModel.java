package com.mmarkley.weatherapp.datamodel;

import android.content.Context;
import android.location.Location;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mmarkley.weatherapp.datamodel.interfaces.WeatherModelCallback;
import com.mmarkley.weatherapp.datamodel.interfaces.WeatherSearchResultsCallback;
import com.mmarkley.weatherapp.datamodel.interfaces.ZipCodeCallback;
import com.mmarkley.weatherapp.datamodel.types.SearchResultType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherModel {
    private static final String BASE_URL = "https://www.metaweather.com/api";

    static WeatherModel theModel;

    private RequestQueue requestQueue;

    public static WeatherModel getInstance() {
        return theModel;
    }
    public static void init(Context context) {
        if(null == theModel) {
            theModel = new WeatherModel(context);
        }
    }

    private WeatherModel(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getWeatherForLocation(@NonNull Location location, @NonNull WeatherModelCallback callback) {
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
        builder.appendPath("location");
        builder.appendPath("search");

    }

    public void getSearchResultsForString(@NonNull String search, @NonNull final WeatherSearchResultsCallback callback) {
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
        builder.appendPath("location");
        builder.appendPath("search");
        builder.appendQueryParameter("query", search);

        StringRequest request = new StringRequest(Request.Method.GET, builder.build().toString(), response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                WeatherSearchResults searchResults = new WeatherSearchResults();
                List<WeatherSearchResult> results = new ArrayList<>();
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject result = jsonArray.getJSONObject(i);
                    WeatherSearchResult weatherSearchResult = new WeatherSearchResult();
                    weatherSearchResult.setTitle(result.getString("title"));
                    weatherSearchResult.setWoeid(result.getInt("woeid"));
                    weatherSearchResult.setLocation_type(SearchResultType.fromString(result.getString("location_type")));
                    String latLongString = result.getString("latt_long");
                    String[] split = latLongString.split(",");
                    if(2 == split.length) {
                        try {
                            String lat = split[0];
                            String lng = split[1];
                            float latFloat = Float.parseFloat(lat);
                            float lngFloat = Float.parseFloat(lng);
                            WeatherLattLong lattLong = new WeatherLattLong(latFloat, lngFloat);
                            weatherSearchResult.setLattLong(lattLong);
                        } catch(NumberFormatException ignored) {

                        }

                    }
                    results.add(weatherSearchResult);
                }
                searchResults.setSearchResults(results);
                callback.onSearchSuccess(searchResults);

            } catch(Exception e) {
                callback.onSearchFailure(e.getCause());
            }
        }, error -> {
            callback.onSearchFailure(error.getCause());
        });
        requestQueue.add(request);
    }

    public void getWeatherForWOEID(int woeid, @NonNull WeatherModelCallback callback) {
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
        builder.appendPath("location");
        builder.appendPath(String.valueOf(woeid));

        StringRequest request = new StringRequest(Request.Method.GET, builder.build().toString(), response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                LocationWeather weather = LocationWeather.fromJsonObject(jsonObject);


            } catch(Exception e) {
                callback.onLocationFailure(e);
            }
        }, error -> {
        });
    }

    public void lookupByZipCode(String zipCode, ZipCodeCallback callback) {
        Uri.Builder builder = Uri.parse("http://maps.googleapis.com/maps/api/geocode/json").buildUpon(); //?address=77379&sensor=true")
        builder.appendQueryParameter("address", zipCode);
        builder.appendQueryParameter("sensor", "true");
        builder.appendQueryParameter("key", "AIzaSyBcFLkwXkPw6cvvK03qIjGN519wakXBGWI");
        StringRequest request = new StringRequest(Request.Method.GET, builder.build().toString(), response -> {
            try {
                System.out.println(response);
            } catch(Exception e) {
            }
        }, error -> {
        });
    }
}

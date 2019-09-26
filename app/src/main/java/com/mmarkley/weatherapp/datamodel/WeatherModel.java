package com.mmarkley.weatherapp.datamodel;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mmarkley.weatherapp.datamodel.interfaces.WeatherSearchResultsCallback;
import com.mmarkley.weatherapp.datamodel.interfaces.ZipCodeCallback;
import com.mmarkley.weatherapp.datamodel.types.SearchResultType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherModel {
    private static final String BASE_URL = "https://www.metaweather.com/api";

    private static WeatherModel theModel;

    private RequestQueue requestQueue;

    public static WeatherModel getInstance() {
        return theModel;
    }

    /**
     * This method must be called before any attempts are made to use the WeatherModel
     * @param context A {@link Context} to use to create the Volley queue
     */
    public static void init(Context context) {
        if(null == theModel) {
            theModel = new WeatherModel(context);
        }
    }

    private WeatherModel(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Method to retrieve geospecific information based on the search string. This has limited
     * capabilities based on text only search. It does not work for addresses or zipcodes
     * @param search A {@link String} to use for searching
     * @param callback A {@link WeatherSearchResultsCallback} that is used to notify the caller
     *                 when the data has been retrieved.
     */
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
        }, error -> callback.onSearchFailure(error.getCause()));
        requestQueue.add(request);
    }

    /**
     * Method to use the Google Geocoding API to try and retrieve geoinformation about a zipcode
     * @param zipCode A {@link String} containing the zipcode to process
     * @param callback A {@link ZipCodeCallback} that is used to notify the caller when data has
     *                 been retrieved.
     */
    public void lookupByZipCode(String zipCode, ZipCodeCallback callback) {
        Uri.Builder builder = Uri.parse("https://maps.googleapis.com/maps/api/geocode/json").buildUpon(); //?address=77379&sensor=true")
        builder.appendQueryParameter("address", zipCode);
        builder.appendQueryParameter("sensor", "true");
        // This really shouldn't be hardcoded, it should be in a keystore. Ran out of time
        builder.appendQueryParameter("key", "AIzaSyDvFtcluOM-S8dYAyCTs6gyedTFONZvEjg");
        StringRequest request = new StringRequest(Request.Method.GET, builder.build().toString(), response -> {
            try {
                Gson gson = new Gson();
                ZipCodeResponse zipResponse = gson.fromJson(response, ZipCodeResponse.class);
                callback.onZipCodeLookupSuccess(zipResponse);
            } catch(Exception e) {
                callback.onZipCodeLookupFailure(e);
            }
        }, callback::onZipCodeLookupFailure);
        requestQueue.add(request);
    }
}

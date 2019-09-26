package com.mmarkley.weatherapp.datamodel;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mmarkley.weatherapp.R;
import com.mmarkley.weatherapp.datamodel.interfaces.CurrentWeatherCallback;
import com.mmarkley.weatherapp.datamodel.interfaces.ForecastWeatherCallback;
import com.mmarkley.weatherapp.datamodel.interfaces.WeatherModelCallback;
import com.mmarkley.weatherapp.datamodel.weatherbit.CurrentWeather;
import com.mmarkley.weatherapp.datamodel.weatherbit.ForecastWeather;

public class WeatherBitModel {
    private static final String CURRENT_CONDITIONS_URL = "https://api.weatherbit.io/v2.0/current";
    private static final String FORECAST_URL = "https://api.weatherbit.io/v2.0/forecast/daily";

    private final String API_KEY;
    private final RequestQueue queue;

    private static WeatherBitModel model = null;

    private WeatherBitModel(Context context) {
        API_KEY = context.getResources().getString(R.string.weatherbit_key);
        queue = Volley.newRequestQueue(context);
    }

    public static void init(Context context) {
        if (null == model) {
            model = new WeatherBitModel(context);
        }
    }

    public static WeatherBitModel getInstance() {
        return model;
    }

    public void getCurrentWeatherForLatLng(double lat, double lng, @NonNull CurrentWeatherCallback callback) {
        Uri.Builder builder = Uri.parse(CURRENT_CONDITIONS_URL).buildUpon();
        builder.appendQueryParameter("lat", String.valueOf(lat));
        builder.appendQueryParameter("lon", String.valueOf(lng));
        builder.appendQueryParameter("units", "I");
        builder.appendQueryParameter("key", API_KEY);

        StringRequest request = new StringRequest(Request.Method.GET, builder.build().toString(), success -> {
            try {
                Gson gson = new Gson();
                CurrentWeather currentWeather = gson.fromJson(success, CurrentWeather.class);
                System.out.println(success.toString());
                callback.onSuccess(currentWeather);
            } catch(Exception e) {
                callback.onFailure(e);
            }
        }, error -> {
            System.err.println(error.toString());
        });

        queue.add(request);
    }

    public void getForecastWeatherForLatLng(double lat, double lng, @NonNull ForecastWeatherCallback callback) {
        Uri.Builder builder = Uri.parse(FORECAST_URL).buildUpon();
        builder.appendQueryParameter("lat", String.valueOf(lat));
        builder.appendQueryParameter("lon", String.valueOf(lng));
        builder.appendQueryParameter("key", API_KEY);

        StringRequest request = new StringRequest(Request.Method.GET, builder.build().toString(), success -> {
            try {
                Gson gson = new Gson();
                ForecastWeather forecastWeather = gson.fromJson(success, ForecastWeather.class);
                callback.onSuccess(forecastWeather);
            } catch (Exception e) {
                callback.onFailure(e);
            }
            System.out.println(success.toString());
        }, error -> {
            System.err.println(error.toString());
        });

        queue.add(request);
    }

    public void getCurrentWeatherForCityCode(long code, WeatherModelCallback callback) {
        Uri.Builder builder = Uri.parse(CURRENT_CONDITIONS_URL).buildUpon();
        builder.appendQueryParameter("city_id", String.valueOf(code));
        builder.appendQueryParameter("key", API_KEY);

        StringRequest request = new StringRequest(Request.Method.GET, builder.build().toString(), success -> {
            System.out.println(success.toString());
        }, error -> {
            System.err.println(error.toString());
        });

        queue.add(request);
    }
}

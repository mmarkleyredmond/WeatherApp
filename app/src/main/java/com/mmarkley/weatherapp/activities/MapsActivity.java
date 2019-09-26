package com.mmarkley.weatherapp.activities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mmarkley.weatherapp.R;
import com.mmarkley.weatherapp.adapters.ExtendedForecastAdapter;
import com.mmarkley.weatherapp.adapters.SearchResultsAdapter;
import com.mmarkley.weatherapp.datamodel.WeatherBitModel;
import com.mmarkley.weatherapp.datamodel.WeatherLattLong;
import com.mmarkley.weatherapp.datamodel.WeatherModel;
import com.mmarkley.weatherapp.datamodel.WeatherSearchResult;
import com.mmarkley.weatherapp.datamodel.WeatherSearchResults;
import com.mmarkley.weatherapp.datamodel.ZipCodeLocation;
import com.mmarkley.weatherapp.datamodel.ZipCodeResponse;
import com.mmarkley.weatherapp.datamodel.ZipCodeResult;
import com.mmarkley.weatherapp.datamodel.ZipGeometry;
import com.mmarkley.weatherapp.datamodel.interfaces.CurrentWeatherCallback;
import com.mmarkley.weatherapp.datamodel.interfaces.ForecastWeatherCallback;
import com.mmarkley.weatherapp.datamodel.interfaces.WeatherSearchResultsCallback;
import com.mmarkley.weatherapp.datamodel.interfaces.ZipCodeCallback;
import com.mmarkley.weatherapp.datamodel.weatherbit.CurrentWeather;
import com.mmarkley.weatherapp.datamodel.weatherbit.EmbeddedWeather;
import com.mmarkley.weatherapp.datamodel.weatherbit.ForecastWeather;
import com.mmarkley.weatherapp.datamodel.weatherbit.WeatherObservation;
import com.mmarkley.weatherapp.settings.Settings;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, WeatherSearchResultsCallback, GoogleMap.OnMapClickListener, CurrentWeatherCallback, ForecastWeatherCallback, SwipeRefreshLayout.OnRefreshListener, ZipCodeCallback {

    private GoogleMap mMap = null;
    RelativeLayout searchLayout;
    RecyclerView recyclerView;
    RecyclerView extendedForecastRecycler;
    EditText searchEntry;
    RelativeLayout currentConditionsLayout;
    ImageView currentWeatherIcon;
    TextView currentConditionsCity;
    TextView currentConditionsTemperature;
    TextView currentConditionsPressure;
    TextView currentConditionsHumidity;
    TextView currentConditionsPrecipitation;
    TextView currentConditionsDescription;
    Button extendedForecastButton;
    RelativeLayout extendedForecastLayout;
    SwipeRefreshLayout swipeLayout;

    boolean refreshing = false;
    String lastSearchText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (null != mapFragment) {
            mapFragment.getMapAsync(this);
        }
        currentConditionsLayout = findViewById(R.id.current_conditions_layout);
        currentWeatherIcon = findViewById(R.id.current_weather_icon);
        currentConditionsCity = findViewById(R.id.current_conditions_city);
        currentConditionsTemperature = findViewById(R.id.current_conditions_temp_value);
        currentConditionsPressure = findViewById(R.id.current_conditions_pressure_value);
        currentConditionsHumidity = findViewById(R.id.current_conditions_humidity_value);
        currentConditionsPrecipitation = findViewById(R.id.current_conditions_precipitation_value);
        currentConditionsDescription = findViewById(R.id.current_conditions_description_value);

        extendedForecastRecycler = findViewById(R.id.extended_forecast_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        extendedForecastRecycler.setLayoutManager(layoutManager);

        extendedForecastLayout = findViewById(R.id.extended_forecast_layout);
        extendedForecastButton = findViewById(R.id.extended_forecast_button);
        extendedForecastButton.setOnClickListener(v -> {
            if (View.GONE == extendedForecastLayout.getVisibility()) {
                LatLng currentLocation = Settings.getSavedLocation(getApplicationContext());
                if (null != currentLocation) {
                    WeatherBitModel.getInstance().getForecastWeatherForLatLng(currentLocation, this);
                }
                extendedForecastButton.setText(R.string.extended_forecast_hide_label);
            } else {
                extendedForecastLayout.setVisibility(View.GONE);
                extendedForecastButton.setText(R.string.extended_forecast_label);
            }
        });

        searchLayout = findViewById(R.id.details_layout);
        searchEntry = findViewById(R.id.location_search_text);
        searchEntry.setOnKeyListener((v, keyCode, event) -> {
            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String searchString = searchEntry.getText().toString();
                processSearchString(searchString);
            }
            return false;
        });

        recyclerView = findViewById(R.id.search_results_recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FloatingActionButton button = findViewById(R.id.change_location_button);
        button.setOnClickListener(v -> {
            if (View.GONE == searchLayout.getVisibility()) {
                searchEntry.setText("");
                currentConditionsLayout.setVisibility(View.INVISIBLE);
                searchLayout.setVisibility(View.VISIBLE);
            } else {
                searchLayout.setVisibility(View.GONE);
            }
        });

        Button goButton = findViewById(R.id.go_button);
        goButton.setOnClickListener(v -> {
            String searchString = searchEntry.getText().toString();
            processSearchString(searchString);
        });

        swipeLayout = findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // See if the user saved a position, if so, show it, otherwise, show Seattle
        LatLng currentLocation = Settings.getSavedLocation(getApplicationContext());
        String name = Settings.getSavedLocationName(getApplicationContext());
        mMap.setOnMapClickListener(this);
        mMap.addMarker(new MarkerOptions().position(currentLocation).title(name));
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(currentLocation).zoom(5.0f);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
        WeatherBitModel.getInstance().getCurrentWeatherForLatLng(currentLocation, this);
    }

    @Override
    public void onSearchSuccess(WeatherSearchResults results) {
        hideTheKeyboard();
        if (refreshing) {
            swipeLayout.setRefreshing(false);
        }
        List<WeatherSearchResult> searchResults = results.getSearchResults();
        if(1 == searchResults.size()) {
            // If there is only one result, just get the info, don't present a list
            WeatherSearchResult weatherSearchResult = searchResults.get(0);
            navigateTo(weatherSearchResult);
            return;
        }
        SearchResultsAdapter adapter = new SearchResultsAdapter(this, results.getSearchResults());
        recyclerView.setAdapter(adapter);
    }

    private void hideTheKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEntry.getWindowToken(), 0);
    }

    @Override
    public void onSearchFailure(Throwable e) {
        if (refreshing) {
            swipeLayout.setRefreshing(false);
        }

    }

    public void navigateTo(WeatherSearchResult searchResult) {
        if (null != mMap) {
            WeatherLattLong lattLong = searchResult.getLattLong();
            LatLng location = new LatLng(lattLong.getLatt(), lattLong.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(location).title(searchResult.getTitle()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            WeatherBitModel.getInstance().getCurrentWeatherForLatLng(location, this);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("New Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        WeatherBitModel.getInstance().getCurrentWeatherForLatLng(latLng, this);
    }

    @Override
    public void onCurrentWeatherSuccess(CurrentWeather currentWeather) {
        searchLayout.setVisibility(View.GONE);
        if (refreshing) {
            swipeLayout.setRefreshing(false);
        }
        if (null != currentWeather && 0 < currentWeather.getCount()) {
            WeatherObservation observation = currentWeather.getData().get(0);
            if (null != observation) {
                currentConditionsCity.setText(observation.getCity_name());
                Settings.saveLocationName(getApplicationContext(), observation.getCity_name());
                LatLng latLng = new LatLng(observation.getLat(), observation.getLon());
                Settings.saveLocation(getApplicationContext(), latLng);
                EmbeddedWeather embeddedWeather = observation.getWeather();
                if (null != embeddedWeather) {
                    String uri = "@drawable/" + embeddedWeather.getIcon();  // where myresource (without the extension) is the file

                    int imageResource = getResources().getIdentifier(uri, null, getPackageName());

                    Drawable res = getResources().getDrawable(imageResource);
                    currentWeatherIcon.setImageDrawable(res);
                    currentConditionsDescription.setText(embeddedWeather.getDescription());
                }
                currentConditionsHumidity.setText(String.valueOf(observation.getRh()));
                currentConditionsPressure.setText(String.valueOf(observation.getPres()));
                currentConditionsPrecipitation.setText(String.valueOf(observation.getPrecip()));
                currentConditionsTemperature.setText(String.valueOf(observation.getTemp()));
            }
            currentConditionsLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onExtendedForecastSuccess(ForecastWeather forecastWeather) {
        if (null != forecastWeather) {
            extendedForecastLayout.setVisibility(View.VISIBLE);
            ExtendedForecastAdapter adapter = new ExtendedForecastAdapter(this, forecastWeather.getData());
            extendedForecastRecycler.setAdapter(adapter);
        }
        if (refreshing) {
            swipeLayout.setRefreshing(false);
        }

    }

    @Override
    public void onExtendedForecastFailure(Throwable throwable) {
        if (refreshing) {
            swipeLayout.setRefreshing(false);
        }
    }

    @Override
    public void onCurrentWeatherFailure(Throwable throwable) {
        if (refreshing) {
            swipeLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        Logger.getLogger(MapsActivity.class.getSimpleName()).log(Level.INFO, "onRefresh");
        refreshing = true;
        if (View.VISIBLE == searchLayout.getVisibility()) {
            if (null != lastSearchText) {
                WeatherModel.getInstance().getSearchResultsForString(lastSearchText, MapsActivity.this);
                Toast.makeText(this, R.string.refresh_search_results, Toast.LENGTH_SHORT).show();
            }
        }
        if (View.VISIBLE == currentConditionsLayout.getVisibility()) {
            Toast.makeText(this, R.string.refresh_current_conditions, Toast.LENGTH_SHORT).show();
            LatLng latLng = Settings.getSavedLocation(this);
            WeatherBitModel.getInstance().getCurrentWeatherForLatLng(latLng, this);
        }
        if (View.VISIBLE == extendedForecastLayout.getVisibility()) {
            LatLng latLng = Settings.getSavedLocation(this);
            WeatherBitModel.getInstance().getForecastWeatherForLatLng(latLng, this);
            Toast.makeText(this, R.string.refresh_forecast_results, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method tests to see if the search string fits the pattern for a zipcode and if so,
     * tries to get the Geo information for the zipcode through the Google Geocoding API. If it
     * doesn't look like a zipcode, it used MetaWeather.com to look up the name.
     * @param searchString A {@link String} containing the text to search with
     */
    private void processSearchString(@NonNull String searchString) {
        // If it's a 5 digit string, then we'll try to process it as a zip code first.
        if (5 == searchString.length()) {
            NumberFormat format = NumberFormat.getIntegerInstance();
            ParsePosition pos = new ParsePosition(0);
            format.parse(searchString, pos);
            if (searchString.length() == pos.getIndex()) {
                WeatherModel.getInstance().lookupByZipCode(searchString, this);
                return;
            }
        }
        WeatherModel.getInstance().getSearchResultsForString(searchString, MapsActivity.this);
    }

    @Override
    public void onZipCodeLookupSuccess(ZipCodeResponse response) {
        hideTheKeyboard();
        List<ZipCodeResult> results = response.getResults();
        if (null != results && 0 < results.size()) {
            ZipCodeResult result = results.get(0);
            ZipGeometry geometry = result.getGeometry();
            ZipCodeLocation location = geometry.getLocation();

            LatLng latLng = new LatLng(location.getLat(), location.getLng());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title(result.getFormatted_address()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            WeatherBitModel.getInstance().getCurrentWeatherForLatLng(latLng, this);
        }
    }

    @Override
    public void onZipCodeLookupFailure(Throwable throwable) {

    }
}

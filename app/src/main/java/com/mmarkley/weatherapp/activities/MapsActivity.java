package com.mmarkley.weatherapp.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mmarkley.weatherapp.R;
import com.mmarkley.weatherapp.adapters.SearchResultsAdapter;
import com.mmarkley.weatherapp.datamodel.WeatherBitModel;
import com.mmarkley.weatherapp.datamodel.WeatherLattLong;
import com.mmarkley.weatherapp.datamodel.WeatherModel;
import com.mmarkley.weatherapp.datamodel.WeatherSearchResult;
import com.mmarkley.weatherapp.datamodel.WeatherSearchResults;
import com.mmarkley.weatherapp.datamodel.interfaces.CurrentWeatherCallback;
import com.mmarkley.weatherapp.datamodel.interfaces.WeatherSearchResultsCallback;
import com.mmarkley.weatherapp.datamodel.weatherbit.CurrentWeather;
import com.mmarkley.weatherapp.datamodel.weatherbit.EmbeddedWeather;
import com.mmarkley.weatherapp.datamodel.weatherbit.WeatherObservation;
import com.mmarkley.weatherapp.settings.Settings;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, WeatherSearchResultsCallback, GoogleMap.OnMapClickListener, CurrentWeatherCallback {

    private GoogleMap mMap = null;
    RelativeLayout searchLayout;
    RecyclerView recyclerView;
    EditText searchEntry;
    RelativeLayout currentConditionsLayout;
    ImageView currentWeatherIcon;
    TextView currentConditionsCity;
    TextView currentConditionsTemperature;
    TextView currentConditionsPressure;
    TextView currentConditionsHumidity;
    TextView currentConditionsPrecipitation;
    TextView currentConditionsDescription;

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


        searchLayout = findViewById(R.id.details_layout);
        searchEntry = findViewById(R.id.location_search_text);
        searchEntry.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String searchString = searchEntry.getText().toString();
                    WeatherModel.getInstance().getSearchResultsForString(searchString, MapsActivity.this);
                }
                return false;
            }
        });
        recyclerView = findViewById(R.id.search_results_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FloatingActionButton button = findViewById(R.id.change_location_button);
        button.setOnClickListener(v -> {
            if (View.GONE == searchLayout.getVisibility()) {
                currentConditionsLayout.setVisibility(View.INVISIBLE);
                searchLayout.setVisibility(View.VISIBLE);
            } else {
                searchLayout.setVisibility(View.GONE);
            }
        });
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

        // Add a marker in Sydney and move the camera

        LatLng currentLocation = Settings.getSavedLocation(getApplicationContext());
        String name = Settings.getSavedLocationName(getApplicationContext());
        mMap.setOnMapClickListener(this);
        mMap.addMarker(new MarkerOptions().position(currentLocation).title(name));
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.target(currentLocation).zoom(5.0f);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
        WeatherBitModel.getInstance().getCurrentWeatherForLatLng(currentLocation.latitude, currentLocation.longitude, this);
    }

    @Override
    public void onSearchSuccess(WeatherSearchResults results) {
        SearchResultsAdapter adapter = new SearchResultsAdapter(this, results.getSearchResults());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSearchFailure(Throwable e) {

    }

    public void navigateTo(WeatherSearchResult searchResult) {
        if (null != mMap) {
            WeatherLattLong lattLong = searchResult.getLattLong();
            LatLng location = new LatLng(lattLong.getLatt(), lattLong.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(location).title(searchResult.getTitle()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            WeatherBitModel.getInstance().getCurrentWeatherForLatLng(lattLong.getLatt(),
                    lattLong.getLongitude(), this);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("New Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        WeatherBitModel.getInstance().getCurrentWeatherForLatLng(latLng.latitude, latLng.longitude, this);
    }

    @Override
    public void onSuccess(CurrentWeather currentWeather) {
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
    public void onFailure(Throwable throwable) {

    }
}

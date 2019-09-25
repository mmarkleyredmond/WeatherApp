package com.mmarkley.weatherapp.activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mmarkley.weatherapp.R;
import com.mmarkley.weatherapp.adapters.SearchResultsAdapter;
import com.mmarkley.weatherapp.datamodel.WeatherLattLong;
import com.mmarkley.weatherapp.datamodel.WeatherModel;
import com.mmarkley.weatherapp.datamodel.WeatherSearchResult;
import com.mmarkley.weatherapp.datamodel.WeatherSearchResults;
import com.mmarkley.weatherapp.datamodel.interfaces.WeatherSearchResultsCallback;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, WeatherSearchResultsCallback {

    private GoogleMap mMap = null;
    RelativeLayout searchLayout;
    RecyclerView recyclerView;
    EditText searchEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(null != mapFragment) {
            mapFragment.getMapAsync(this);
        }
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
        Button button = findViewById(R.id.change_location_button);
        button.setOnClickListener(v -> {
            if(View.GONE == searchLayout.getVisibility()) {
                searchLayout.setVisibility(View.VISIBLE);
                ((Button)v).setText(R.string.change_location_alternate_title);
            } else {
                searchLayout.setVisibility(View.GONE);
                ((Button)v).setText(R.string.change_location_button_title);
            }
        });
//        WeatherModel.getInstance().getSearchResultsForString("san", this);
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
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
        if(null != mMap) {
            WeatherLattLong lattLong = searchResult.getLattLong();
            LatLng location = new LatLng(lattLong.getLatt(), lattLong.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(location).title(searchResult.getTitle()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    }
}

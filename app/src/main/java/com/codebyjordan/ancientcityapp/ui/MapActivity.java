package com.codebyjordan.ancientcityapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.codebyjordan.ancientcityapp.maps.BaseMapActivity;
import com.codebyjordan.ancientcityapp.maps.MapMarkers;
import com.codebyjordan.ancientcityapp.yelp.models.Business;
import com.codebyjordan.ancientcityapp.yelp.models.BusinessLocationCoords;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends BaseMapActivity {

    private GoogleMap mMap;
    private MapMarkers mMarker;
    private LatLng mIntentCoords;
    private Business mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent.getParcelableExtra("key_place") != null
                && intent.getParcelableExtra("key_coords") != null) {
            Business place = intent.getParcelableExtra("key_place");
            BusinessLocationCoords coords = intent.getParcelableExtra("key_coords");

            mIntentCoords = new LatLng(coords.getLatitude(), coords.getLongitude());
            mPlace = place;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        mMap = googleMap;
        mMarker = new MapMarkers(mMap);

        if(mIntentCoords != null) {
            String tag = mPlace.getId();
            mMarker.make(mIntentCoords, tag);
        }

    }
}
package com.codebyjordan.ancientcityapp.maps;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapMarkers {

    private GoogleMap mMap;

    public MapMarkers(GoogleMap map) {
        mMap = map;
    }

    public void make(LatLng coords, String tag) {
        MarkerOptions options = new MarkerOptions()
                .position(coords);

        Marker marker = mMap.addMarker(options);
        marker.setTag(tag);

    }
}

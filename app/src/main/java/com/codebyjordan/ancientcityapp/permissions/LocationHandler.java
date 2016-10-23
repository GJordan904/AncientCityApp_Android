package com.codebyjordan.ancientcityapp.permissions;

import com.google.android.gms.maps.GoogleMap;

public class LocationHandler implements ResponseInterfaces.LocationSuccessHandler {
    @Override
    public void responseMethod(GoogleMap gMap) {
        gMap.setMyLocationEnabled(true);
    }
}

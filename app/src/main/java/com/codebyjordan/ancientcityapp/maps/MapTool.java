package com.codebyjordan.ancientcityapp.maps;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.codebyjordan.ancientcityapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

public class MapTool {

    private GoogleMap mMap;
    private Context mContext;

    public MapTool(GoogleMap map, Context context) {
        mMap = map;
        mContext = context;
    }

    public void setupUi() {
        mMap.setBuildingsEnabled(false);
        UiSettings settings =  mMap.getUiSettings();
        settings.setCompassEnabled(false);
        settings.setMyLocationButtonEnabled(false);
        settings.setMapToolbarEnabled(false);
    }

    public void styleMap() {
        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(mContext, R.raw.map_style));

            if (!success) {
                Log.e("MapActivityRaw", "Style parsing failed");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapActivityRaw", "Can't find style: ", e);
        }
    }

    public void moveCamera(LatLng target, int zoom, int tilt) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(target)
                .zoom(zoom)
                .tilt(tilt)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}

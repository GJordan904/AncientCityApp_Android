package com.codebyjordan.ancientcityapp.gmaps;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.permissions.LocationHandler;
import com.codebyjordan.ancientcityapp.permissions.PermissionRequester;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, OnRequestPermissionsResultCallback {

    private final LatLng ST_AUG = new LatLng(29.891971, -81.314147);

    private GoogleMap mMap;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mView = findViewById(R.id.mapLayout);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Check for permission to access user location data
        PermissionRequester requester = new PermissionRequester(this, mView, 0, new LocationHandler());
        requester.setLocationPermission(mMap);

        // Style Map
        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

            if (!success) {
                Log.e("MapActivityRaw", "Style parsing failed");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapActivityRaw", "Can't find style: ", e);
        }

        // Move Camera
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(ST_AUG)
                .zoom(15)
                .tilt(45)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

}

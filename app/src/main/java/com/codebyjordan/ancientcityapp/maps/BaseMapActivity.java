package com.codebyjordan.ancientcityapp.maps;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.listeners.StartActivityOnClick;
import com.codebyjordan.ancientcityapp.permissions.LocationHandler;
import com.codebyjordan.ancientcityapp.permissions.PermissionRequester;
import com.codebyjordan.ancientcityapp.ui.ParkingActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;

public class BaseMapActivity extends AppCompatActivity
        implements OnMapReadyCallback, OnRequestPermissionsResultCallback, GoogleApiClient.OnConnectionFailedListener {

    private final LatLng ST_AUG = new LatLng(29.891971, -81.314147);
    private final String TAG = BaseMapActivity.class.getSimpleName();

    private GoogleMap mMap;
    private View mView;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private boolean mPermissionGranted;

    public Location getLastLocation() {
        if(isPermissionGranted()) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        return mLastLocation;
    }

    public boolean isPermissionGranted() {
        return mPermissionGranted;
    }

    public GoogleApiClient getApiClient() {
        return  mGoogleApiClient;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mView = findViewById(R.id.mapLayout);

        // Create Toolbar and set as action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(myToolbar);
        // Get support ActionBar and set up button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Get google map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Redirect parking button to parking activity
        Button parkingBtn = (Button) findViewById(R.id.parkingButton);
        parkingBtn.setOnClickListener(new StartActivityOnClick(this, ParkingActivity.class));

        // Start Google Play Services
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Disable some ui settings
        mMap.setBuildingsEnabled(false);
        UiSettings settings =  mMap.getUiSettings();
        settings.setCompassEnabled(false);
        settings.setMyLocationButtonEnabled(false);
        settings.setMapToolbarEnabled(false);

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

        // Check for permission to access user location data
        PermissionRequester requester = new PermissionRequester(this, mView, 0, new LocationHandler());
        mPermissionGranted = requester.setLocationPermission(mMap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mPermissionGranted = true;
            mMap.setMyLocationEnabled(true);
        }else {
            mPermissionGranted = false;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Connection to google places API failed");
    }
}

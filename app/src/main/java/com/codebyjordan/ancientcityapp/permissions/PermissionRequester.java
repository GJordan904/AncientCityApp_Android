package com.codebyjordan.ancientcityapp.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import com.codebyjordan.ancientcityapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.codebyjordan.ancientcityapp.permissions.ResponseInterfaces.LocationSuccessHandler;

public class PermissionRequester {

    private Context mContext;
    private View mView;
    private int mPermissionId;
    private LocationSuccessHandler mLocationHandler;
    private GoogleMap mMap;

    public PermissionRequester(Context context, View view, int permissionId, LocationSuccessHandler handler) {
        mContext = context;
        mView= view;
        mPermissionId = permissionId;
        mLocationHandler = handler;
    }

    public void setLocationPermission(GoogleMap gMap) {

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationHandler.responseMethod(gMap);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Display a snackbar w/ reason and ask for permission on clicking ok
                Snackbar.make(mView, R.string.permissions_location_rationale, Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestFineLocation();
                            }
                        })
                        .show();
            }else{
                requestFineLocation();
            }
        }
    }

    private void requestFineLocation() {
        ActivityCompat.requestPermissions((Activity) mContext,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                mPermissionId);
    }
}

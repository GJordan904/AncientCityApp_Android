package com.codebyjordan.ancientcityapp.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.dialogs.ParkingDialog;
import com.codebyjordan.ancientcityapp.maps.BaseMapActivity;
import com.codebyjordan.ancientcityapp.maps.FindClosestParking;
import com.codebyjordan.ancientcityapp.maps.MyKmlLayers;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.kml.KmlLayer;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.*;


public class ParkingActivity extends BaseMapActivity {
    private final String TAG = "Parking Activity";

    private GoogleMap mMap;
    private KmlLayer mStreetParking;
    private KmlLayer mParkingLots;
    private HashMap<String, KmlLayer> mLayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayers = new HashMap<>();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        mMap = googleMap;

        // Get KML Layers and set variables
        MyKmlLayers mMyKmlLayers = new MyKmlLayers(this, mMap);
        mLayers = mMyKmlLayers.getKmlLayers();
        mStreetParking = mLayers.get("streetParking");
        mParkingLots = mLayers.get("parkingLots");


        // Create Dialog
        final DialogFragment dialog = new ParkingDialog(this, mMap, getApiClient(), mLayers);

        // Set Parking Button click handler and open parking dialog
        Button parkingButton = (Button) findViewById(R.id.parkingButton);
        parkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(getFragmentManager(), "ParkingDialog");
            }
        });
        dialog.show(getFragmentManager(), "ParkingDialog");

    }

    public void onCheckBoxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.cbStreets:
                ifChecked(checked, mStreetParking);
                break;
            case R.id.cbLots:
                ifChecked(checked, mParkingLots);
                break;
            case R.id.cbFreeAfterFive:

                break;
        }
    }

    private void ifChecked(boolean checked, KmlLayer layer) {
        if(checked) {
            try {
                layer.addLayerToMap();
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
        }else layer.removeLayerFromMap();
    }

    // Inner Class for Dialog Fragment. I made it an inner class so it has access to the activities methods

}

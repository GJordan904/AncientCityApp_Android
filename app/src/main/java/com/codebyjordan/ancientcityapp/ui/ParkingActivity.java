package com.codebyjordan.ancientcityapp.ui;


import android.app.DialogFragment;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.dialogs.ParkingDialog;
import com.codebyjordan.ancientcityapp.maps.BaseMapActivity;
import com.codebyjordan.ancientcityapp.maps.DirectionsToParking;
import com.codebyjordan.ancientcityapp.maps.DirectionsToParking.ClosestParkingResponse;
import com.codebyjordan.ancientcityapp.maps.MyKmlLayers;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.maps.android.kml.KmlLayer;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.*;


public class ParkingActivity extends BaseMapActivity implements ClosestParkingResponse {

    private final String TAG = ParkingActivity.class.getSimpleName();

    private GoogleMap mMap;
    private KmlLayer mStreetParking;
    private KmlLayer mParkingLots;
    private KmlLayer mTestingLayer;
    private HashMap<String, KmlLayer> mLayers;
    private Polygon mClosestParkingPoly;
    private Polyline mDirectionsLine;
    private Marker mClosestParkingMark;

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
        mStreetParking = mLayers.get(MyKmlLayers.STREET_PARKING_TAG);
        mParkingLots = mLayers.get(MyKmlLayers.PARKING_LOTS_TAG);
        mTestingLayer = mLayers.get(MyKmlLayers.TESTING_TAG);


        // Create Dialog
        final DialogFragment dialog = ParkingDialog.newInstance(mMap);

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

    @Override
    public void processFinish(Polygon polygon, Polyline line, Marker marker) {
        mClosestParkingPoly = polygon;
        mDirectionsLine = line;
        mClosestParkingMark = marker;
    }

    public void onCheckBoxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        Log.v(TAG, "onCheckBoxCLicked: " + checked + ", " + view.getId());

        switch(view.getId()) {
            case R.id.cbStreets:
                ifLayerChecked(checked, mStreetParking);
                break;
            case R.id.cbLots:
                ifLayerChecked(checked, mParkingLots);
                break;
            case R.id.cbFreeAfterFive:

                break;
            case R.id.cbNearest:
                if(checked) {
                    Location lastKnown = getLastLocation();
                    new DirectionsToParking(this, this,  mMap, lastKnown, mLayers).execute();
                }else{
                    if(mClosestParkingPoly !=null) {
                        mClosestParkingPoly.remove();
                        mDirectionsLine.remove();
                        mClosestParkingMark.remove();
                    }
                }
                break;
        }
    }

    private void ifLayerChecked(boolean checked, KmlLayer layer) {
        Log.v(TAG, "ifLayerChecked: " + checked);
        if(checked) {
            Log.v(TAG, "Adding Layer");
            try {
                layer.addLayerToMap();
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
        }else {
            Log.v(TAG, "Layer Removed");
            layer.removeLayerFromMap();
        }
    }
}

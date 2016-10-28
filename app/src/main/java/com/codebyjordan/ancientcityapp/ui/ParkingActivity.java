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
        final DialogFragment dialog = new ParkingDialog();

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
    public class ParkingDialog extends DialogFragment {

        private static final String STREET_TAG = "CHECKBOX_STREET_SETTING";
        private static final String LOTS_TAG = "CHECKBOX_LOT_SETTINGS";
        private static final String FREE_TAG = "CHECKBOX_FREE_TAG";
        private CheckBox mStreetCb;
        private CheckBox mLotsCb;
        private CheckBox mFreeCb;
        private Button mOk;
        private Button mClear;
        private Button mClosest;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_parking_menu, null);
            // Set Retain instance to keep reference to mLayers
            setRetainInstance(true);

            // Buttons
            mOk = (Button )view.findViewById(R.id.parkingOkBtn);
            mClear = (Button) view.findViewById(R.id.parkingClearBtn);
            mClosest = (Button) view.findViewById(R.id.closestParking);

            // CheckBoxes
            mStreetCb = (CheckBox) view.findViewById(R.id.cbStreets);
            mLotsCb = (CheckBox) view.findViewById(R.id.cbLots);
            mFreeCb = (CheckBox) view.findViewById(R.id.cbFreeAfterFive);

            // Set On cLick listeners
            mOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Save checkbox states
                    setCheckedSettings(mStreetCb.isChecked(), STREET_TAG);
                    setCheckedSettings(mLotsCb.isChecked(), LOTS_TAG);
                    setCheckedSettings(mFreeCb.isChecked(), FREE_TAG);
                    // Close Dialog
                    dismiss();
                }
            });

            mClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Uncheck all boxes and save to preferences
                    mStreetCb.setChecked(false);
                    mLotsCb.setChecked(false);
                    mFreeCb.setChecked(false);
                    setCheckedSettings(mStreetCb.isChecked(), STREET_TAG);
                    setCheckedSettings(mLotsCb.isChecked(), LOTS_TAG);
                    setCheckedSettings(mFreeCb.isChecked(), FREE_TAG);
                    // Clear Layers from map
                    for(KmlLayer layer : mLayers.values()) layer.removeLayerFromMap();
                    // Close Dialog
                    dismiss();
                }
            });

            mClosest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get Last Location, send lat lng in intent to the Directions Service
                    Log.v(TAG, Boolean.toString(isPermissionGranted()));
                    Location lastKnown = getLastLocation();
                    new FindClosestParking(ParkingActivity.this, mMap, lastKnown, mLayers).execute();

                }
            });

            // Retrieve options state
            mStreetCb.setChecked(isCheckSettingEnabled(STREET_TAG));
            mLotsCb.setChecked(isCheckSettingEnabled(LOTS_TAG));
            mFreeCb.setChecked(isCheckSettingEnabled(FREE_TAG));

            builder.setView(view);
            return builder.create();
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            super.onCancel(dialog);
            setCheckedSettings(mStreetCb.isChecked(), STREET_TAG);
            setCheckedSettings(mLotsCb.isChecked(), LOTS_TAG);
            setCheckedSettings(mFreeCb.isChecked(), FREE_TAG);
        }

        private void setCheckedSettings(boolean checked, String tag) {
            PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .edit()
                    .putBoolean(tag, checked)
                    .apply();
        }

        private boolean isCheckSettingEnabled(String tag) {
            return PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .getBoolean(tag, false);
        }

    }
}

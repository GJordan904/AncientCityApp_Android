package com.codebyjordan.ancientcityapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
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
import com.codebyjordan.ancientcityapp.maps.FindClosestParking;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.kml.KmlLayer;

import java.util.HashMap;

public class ParkingDialog extends DialogFragment {

    private static final String STREET_TAG = "CHECKBOX_STREET_SETTING";
    private static final String LOTS_TAG = "CHECKBOX_LOT_SETTINGS";
    private static final String FREE_TAG = "CHECKBOX_FREE_TAG";
    private HashMap<String, KmlLayer> mLayers;
    private Context mContext;
    private GoogleMap mMap;
    private GoogleApiClient mClient;
    private CheckBox mStreetCb;
    private CheckBox mLotsCb;
    private CheckBox mFreeCb;


    public ParkingDialog(Context context, GoogleMap map, GoogleApiClient client, HashMap<String, KmlLayer> layers) {
        mContext = context;
        mMap = map;
        mClient = client;
        mLayers = layers;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_parking_menu, null);
        // Set Retain instance to keep reference to mLayers
        setRetainInstance(true);

        // Buttons
        Button mOk = (Button )view.findViewById(R.id.parkingOkBtn);
        Button mClear = (Button) view.findViewById(R.id.parkingClearBtn);
        Button mClosest = (Button) view.findViewById(R.id.closestParking);

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
                Location lastKnown = LocationServices.FusedLocationApi.getLastLocation(mClient);
                new FindClosestParking(mContext, mMap, lastKnown, mLayers).execute();

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

package com.codebyjordan.ancientcityapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import com.codebyjordan.ancientcityapp.R;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ParkingDialog extends DialogFragment {

    private static final String STREET_TAG = "CHECKBOX_STREET_TAG";
    private static final String LOTS_TAG = "CHECKBOX_LOT_TAG";
    private static final String FREE_TAG = "CHECKBOX_FREE_TAG";
    private static final String NEAREST_TAG = "CHECKBOX_NEAREST_TAG";
    private static final String NEAREST_THREE_TAG = "CHECKBOX_NEAREST_THREE_TAG";
    private HashMap<String, CheckBox> mCbMap = new HashMap<>();
    private GoogleMap mMap;


    public static ParkingDialog newInstance(GoogleMap map) {
        ParkingDialog dialog = new ParkingDialog();
        dialog.setMap(map);
        return dialog;
    }

    public void setMap(GoogleMap map){
        mMap = map;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_parking_menu, null);
        // Set Retain instance to keep reference to mMap
        setRetainInstance(true);

        // Buttons
        Button mOk = (Button )view.findViewById(R.id.parkingOkBtn);
        Button mClear = (Button) view.findViewById(R.id.parkingClearBtn);

        // CheckBoxes
        CheckBox streetCb = (CheckBox) view.findViewById(R.id.cbStreets);
        mCbMap.put(STREET_TAG, streetCb);
        CheckBox lotsCb = (CheckBox) view.findViewById(R.id.cbLots);
        mCbMap.put(LOTS_TAG, lotsCb);
        CheckBox freeCb = (CheckBox) view.findViewById(R.id.cbFreeAfterFive);
        mCbMap.put(FREE_TAG, freeCb);
        CheckBox nearestCb = (CheckBox) view.findViewById(R.id.cbNearest);
        mCbMap.put(NEAREST_TAG, nearestCb);
        CheckBox threeNearestCb = (CheckBox) view.findViewById(R.id.cbThreeNearest);
        mCbMap.put(NEAREST_THREE_TAG, threeNearestCb);

        // Set On cLick listeners
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save checkbox states
                for(Map.Entry entry : mCbMap.entrySet()) {
                    String tag = entry.getKey().toString();
                    CheckBox cb = (CheckBox) entry.getValue();

                    setCheckedSettings(cb.isChecked(), tag);
                }
                // Close Dialog
                dismiss();
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Uncheck all boxes and save to preferences
                for(Map.Entry entry : mCbMap.entrySet()) {
                    String tag = entry.getKey().toString();
                    CheckBox cb = (CheckBox) entry.getValue();

                    cb.setChecked(false);
                    setCheckedSettings(cb.isChecked(), tag);
                }
                // Clear everything from map
                mMap.clear();
                // Close Dialog
                dismiss();
            }
        });

        // Retrieve options state
        for(Map.Entry entry : mCbMap.entrySet()) {
            String tag = entry.getKey().toString();
            CheckBox cb = (CheckBox) entry.getValue();

            if(isCheckSettingEnabled(tag)) {
                cb.setChecked(true);
            }
        }

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        for(Map.Entry entry : mCbMap.entrySet()) {
            String tag = entry.getKey().toString();
            CheckBox cb = (CheckBox) entry.getValue();
            setCheckedSettings(cb.isChecked(), tag);
        }
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

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
import com.codebyjordan.ancientcityapp.maps.ParkingAreas;

public class ParkingDialog extends DialogFragment {

    private static final String STREET_TAG = "CHECKBOX_STREET_SETTING";
    private static final String LOTS_TAG = "CHECKBOX_LOT_SETTINGS";
    private static final String FREE_TAG = "CHECKBOX_FREE_TAG";
    private CheckBox mStreet;
    private CheckBox mLots;
    private CheckBox mFree;
    private Button mOk;
    private Button mClear;
    private ParkingAreas mParking;

    public static ParkingDialog newInstance(ParkingAreas parking) {
        ParkingDialog dialog = new ParkingDialog();
        dialog.setParking(parking);
        return dialog;
    }

    public void setParking(ParkingAreas parking) {
        mParking = parking;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_parking_menu, null);
        // Set Retain instance to keep reference to parking object
        setRetainInstance(true);

        // Buttons
        mOk = (Button )view.findViewById(R.id.parkingOkBtn);
        mClear = (Button) view.findViewById(R.id.parkingClearBtn);

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save checkbox states
                setCheckedSettings(mStreet.isChecked(), STREET_TAG);
                setCheckedSettings(mLots.isChecked(), LOTS_TAG);
                setCheckedSettings(mFree.isChecked(), FREE_TAG);
                // Close Dialog
                dismiss();
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Uncheck all boxes
                mStreet.setChecked(false);
                mLots.setChecked(false);
                mFree.setChecked(false);
                setCheckedSettings(mStreet.isChecked(), STREET_TAG);
                setCheckedSettings(mLots.isChecked(), LOTS_TAG);
                setCheckedSettings(mFree.isChecked(), FREE_TAG);
                mParking.clearAll();
                // Close Dialog
                dismiss();
            }
        });

        // CheckBoxes
        mStreet = (CheckBox) view.findViewById(R.id.cbStreets);
        mLots = (CheckBox) view.findViewById(R.id.cbLots);
        mFree = (CheckBox) view.findViewById(R.id.cbFreeAfterFive);

        // Retrieve options state
        mStreet.setChecked(isCheckSettingEnabled(STREET_TAG));
        mLots.setChecked(isCheckSettingEnabled(LOTS_TAG));
        mFree.setChecked(isCheckSettingEnabled(FREE_TAG));

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        setCheckedSettings(mStreet.isChecked(), STREET_TAG);
        setCheckedSettings(mLots.isChecked(), LOTS_TAG);
        setCheckedSettings(mFree.isChecked(), FREE_TAG);
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

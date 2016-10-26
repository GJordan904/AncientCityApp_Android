package com.codebyjordan.ancientcityapp.ui;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.dialogs.ParkingDialog;
import com.codebyjordan.ancientcityapp.maps.BaseMapActivity;
import com.codebyjordan.ancientcityapp.maps.ParkingAreas;
import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;


public class ParkingActivity extends BaseMapActivity {

    private GoogleMap mMap;
    private ParkingAreas parking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        mMap = googleMap;

        parking = new ParkingAreas(this, mMap);

        final DialogFragment dialog = ParkingDialog.newInstance(parking);

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
                if(checked) {
                    try {
                        parking.drawStreets();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else parking.clearStreets();
                break;
            case R.id.cbLots:
                if(checked) {
                    try {
                        parking.drawLots();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else parking.clearLots();
                break;
            case R.id.cbFreeAfterFive:
                if(checked) {
                    try {
                        parking.drawFreeAfterFive();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else parking.clearFreeAfterFive();
                break;
        }
    }
}

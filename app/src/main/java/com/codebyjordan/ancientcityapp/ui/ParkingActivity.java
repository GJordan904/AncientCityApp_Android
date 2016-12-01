package com.codebyjordan.ancientcityapp.ui;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.view.*;
import android.widget.Button;
import android.widget.ProgressBar;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.maps.BaseMapActivity;
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

    private static final String MENU_TAG = "MENU_FRAGMENT";
    private static final String MAP_TITLE = "Parking Map";
    private static final String MENU_TITLE = "Parking Menu";
    private static ActionBar actionBar;
    private static boolean isMenuVisible = false;
    private static KmlLayer streetParking;
    private static KmlLayer parkingLots;

    private GoogleMap mMap;
    private KmlLayer mTestingLayer;
    private HashMap<String, KmlLayer> mLayers;
    private Polygon mClosestParkingPoly;
    private Polyline mDirectionsLine;
    private Marker mClosestParkingMark;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressBar = (ProgressBar) findViewById(R.id.parkingLoading);
        actionBar = getSupportActionBar();
        mLayers = new HashMap<>();
        Button parkingButton = (Button) findViewById(R.id.parkingButton);
        parkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if(!isMenuVisible) {
                    ft.add(R.id.menu_placeholder, new ParkingMenuFragment(), MENU_TAG);
                    ft.commit();
                    actionBar.setTitle(MENU_TITLE);
                    isMenuVisible = true;
                }else{
                    ft.remove(getSupportFragmentManager().findFragmentByTag(MENU_TAG));
                    ft.commit();
                    actionBar.setTitle(MAP_TITLE);
                    isMenuVisible = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_parking, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                if(isMenuVisible) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.remove(getSupportFragmentManager().findFragmentByTag(MENU_TAG));
                    ft.commit();
                    actionBar.setTitle(MAP_TITLE);
                    isMenuVisible = false;
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        mMap = googleMap;

        // Get KML Layers and set variables
        MyKmlLayers mMyKmlLayers = new MyKmlLayers(this, mMap);
        mLayers = mMyKmlLayers.getKmlLayers();
        streetParking = mLayers.get(MyKmlLayers.STREET_PARKING_TAG);
        parkingLots = mLayers.get(MyKmlLayers.PARKING_LOTS_TAG);
        //mTestingLayer = mLayers.get(MyKmlLayers.TESTING_TAG);
    }

    @Override
    public void processFinish(Polygon polygon, Polyline line, Marker marker) {
        mClosestParkingPoly = polygon;
        mDirectionsLine = line;
        mClosestParkingMark = marker;
        mProgressBar.setVisibility(View.GONE);
    }

    //
    // Menu Fragment
    //
    public static class ParkingMenuFragment extends Fragment implements View.OnClickListener{

        private CardView mClosestCard;
        private CardView mLotsCard;
        private CardView mStreetCard;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_parking_menu, container, false);

            mClosestCard = (CardView) view.findViewById(R.id.routeToNearest);
            mClosestCard.setOnClickListener(this);
            mLotsCard = (CardView) view.findViewById(R.id.parkingLots);
            mLotsCard.setOnClickListener(this);
            mStreetCard = (CardView) view.findViewById(R.id.streetParking);
            mStreetCard.setOnClickListener(this);

            return view;
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.routeToNearest:

                    closeFragment();
                    break;
                case R.id.parkingLots:
                    try {
                        parkingLots.addLayerToMap();
                    } catch (IOException | XmlPullParserException e) {
                        e.printStackTrace();
                    }
                    closeFragment();
                    break;
                case R.id.streetParking:
                    try {
                        streetParking.addLayerToMap();
                    } catch (IOException | XmlPullParserException e) {
                        e.printStackTrace();
                    }
                    closeFragment();
                    break;
            }
        }

        private void closeFragment() {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fm.findFragmentByTag(MENU_TAG));
            ft.commit();
            actionBar.setTitle(MAP_TITLE);
            isMenuVisible = false;
        }
    }
}

package com.codebyjordan.ancientcityapp.maps;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.codebyjordan.ancientcityapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.kml.KmlContainer;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.kml.KmlPlacemark;
import com.google.maps.android.kml.KmlPolygon;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;


public class MyKmlLayers{

    private final String TAG = MyKmlLayers.class.getSimpleName();
    private final String STREET_PARKING_TAG = "streetParking";
    private final String PARKING_LOTS_TAG = "parkingLots";
    private final String TESTING_TAG =  "testing";

    private HashMap<String, KmlLayer> mLayers;
    private HashMap<String, KmlPolygon> mPolygons;
    private GoogleMap mMap;
    private Context mContext;

    public MyKmlLayers(Context context, GoogleMap map) {
        mContext = context;
        mMap = map;
        mLayers = new HashMap<>();
    }

    public MyKmlLayers(HashMap<String, KmlLayer> layers) {
        mLayers = layers;
        mPolygons = new HashMap<>();
    }

    public HashMap<String, KmlLayer> getKmlLayers() {
        try {
            mLayers.put(STREET_PARKING_TAG ,new KmlLayer(mMap, R.raw.street_parking, mContext));
            mLayers.put(PARKING_LOTS_TAG, new KmlLayer(mMap, R.raw.parking_lots, mContext));
            mLayers.put(TESTING_TAG, new KmlLayer(mMap, R.raw.testing, mContext));
        }catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return mLayers;
    }

    public HashMap<String, KmlPolygon>getKmlPolygons() {
        for(KmlLayer layer : mLayers.values()) {
            for(KmlContainer container : layer.getContainers()) {
                for (KmlPlacemark placemark : container.getPlacemarks()) {
                    mPolygons.put(placemark.getProperty("name"), (KmlPolygon) placemark.getGeometry());
                }
            }
        }
        return mPolygons;
    }

}

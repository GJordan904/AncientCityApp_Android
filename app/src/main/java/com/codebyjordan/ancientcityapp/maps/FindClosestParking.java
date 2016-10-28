package com.codebyjordan.ancientcityapp.maps;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import com.codebyjordan.ancientcityapp.directions.DirectionCallback;
import com.codebyjordan.ancientcityapp.directions.GoogleDirection;
import com.codebyjordan.ancientcityapp.directions.model.Direction;
import com.codebyjordan.ancientcityapp.directions.model.Leg;
import com.codebyjordan.ancientcityapp.directions.model.Route;
import com.codebyjordan.ancientcityapp.directions.util.DirectionConverter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.kml.KmlPolygon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindClosestParking extends AsyncTask<Void, Void, Void>{

    private final String TAG = FindClosestParking.class.getSimpleName();

    private Context mContext;
    private GoogleMap mMap;
    private Location mLastKnown;
    private HashMap<String, KmlLayer> mLayers;
    private HashMap<String, KmlPolygon> mPolygons;
    private LatLng mOrigin;
    private Route mShortestRoute;

    public FindClosestParking(Context context, GoogleMap map, Location lastKnown, HashMap<String, KmlLayer> layers) {
        mContext = context;
        mMap = map;
        mLastKnown = lastKnown;
        mLayers = layers;
        mPolygons = new MyKmlLayers(mLayers).getKmlPolygons();
        mOrigin = new LatLng(mLastKnown.getLatitude(), mLastKnown.getLongitude());
    }

    @Override
    protected Void doInBackground(Void... params) {
        String apiKey = "AIzaSyD3IJm-5PMPgKnmOKPqVtOvHzogioiJjys";
        // For every point within 1000 meters: lookup directions and compare the distance, save point with the shortest distance
        for(LatLng point : nearbyPoints()) {
            Direction direction = GoogleDirection.withServerKey(apiKey)
                    .from(mOrigin)
                    .to(point)
                    .execute();

            if(direction.isOK()){
                if(mShortestRoute == null) {
                    mShortestRoute =  direction.getRouteList().get(0);
                }
                else {
                    Log.v(TAG, "mShortestRoute inside : " + mShortestRoute);
                    Leg legFromShortest = mShortestRoute.getLegList().get(0);
                    Leg legToCheck = direction.getRouteList().get(0).getLegList().get(0);
                    int distanceFromShortest = Integer.parseInt(legFromShortest.getDistance().getValue());
                    int distanceToCheck = Integer.parseInt(legToCheck.getDistance().getValue());

                    if(distanceToCheck < distanceFromShortest) {
                        mShortestRoute = direction.getRouteList().get(0);
                    }
                }
                Log.v(TAG, mShortestRoute.getLegList().get(0).getDistance().getText());
                // After getting point with shortest distance create polyline to display on the map
            }else{
                Log.e(TAG, direction.getErrorMessage());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ArrayList<LatLng> directionPoints = mShortestRoute.getLegList().get(0).getDirectionPoint();
        PolylineOptions options = DirectionConverter.createPolyline(mContext, directionPoints, 5, Color.RED);
        mMap.addPolyline(options);
    }

    // Find polygons that are within 1000 meters of the devices location
    private List<LatLng> nearbyPoints() {
        List<LatLng> nearby = new ArrayList<>();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        // For each Polygon find its center and add it to the list if its within 1000 meters of device location
        for(Map.Entry entry : mPolygons.entrySet()) {
            String polygonName = entry.getKey().toString();
            KmlPolygon polygon = (KmlPolygon) entry.getValue();
            // For each point on the polygon
            for(int i = 0; i < polygon.getGeometryObject().size(); i++) {
                // Create bounds with points
                for (LatLng point : polygon.getGeometryObject().get(i)) {
                    LatLng cords = new LatLng(point.latitude, point.longitude);
                    builder.include(cords);
                }
            }
            LatLngBounds bounds = builder.build();
            LatLng center = bounds.getCenter();

            Location target = new Location("");
            target.setLatitude(center.latitude);
            target.setLongitude(center.longitude);

            if(target.distanceTo(mLastKnown) < 1000) nearby.add(center);
        }
        return nearby;
    }
}

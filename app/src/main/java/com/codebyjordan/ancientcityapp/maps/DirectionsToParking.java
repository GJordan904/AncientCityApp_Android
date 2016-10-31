package com.codebyjordan.ancientcityapp.maps;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import com.codebyjordan.ancientcityapp.directions.GoogleDirection;
import com.codebyjordan.ancientcityapp.directions.model.Direction;
import com.codebyjordan.ancientcityapp.directions.model.Leg;
import com.codebyjordan.ancientcityapp.directions.model.Route;
import com.codebyjordan.ancientcityapp.directions.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.*;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.kml.KmlPlacemark;
import com.google.maps.android.kml.KmlPolygon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DirectionsToParking extends AsyncTask<Void, Void, Route>{

    private final String TAG = DirectionsToParking.class.getSimpleName();

    private Context mContext;
    private GoogleMap mMap;
    private Location mLastKnown;
    private HashMap<String, KmlLayer> mLayers;
    private HashMap<String, KmlPolygon> mPolygons;
    private LatLng mOrigin;
    private String mClosestParking;
    private boolean mDidReturnPoints = false;
    private LatLng mClosestCenter;
    private ClosestParkingResponse mFinished;
    private MyKmlLayers mMyKmlLayers;

    public interface ClosestParkingResponse {
        void processFinish(Polygon polygon, Polyline line, Marker marker);
    }

    public DirectionsToParking(Context context,
                               ClosestParkingResponse finished,
                               GoogleMap map,
                               Location lastKnown,
                               HashMap<String, KmlLayer> layers) {
        mContext = context;
        mFinished = finished;
        mMap = map;
        mLastKnown = lastKnown;
        mLayers = layers;
        mMyKmlLayers = new MyKmlLayers(mLayers);
        mPolygons = mMyKmlLayers.getKmlPolygons();
        mOrigin = new LatLng(mLastKnown.getLatitude(), mLastKnown.getLongitude());
    }

    @Override
    protected Route doInBackground(Void... params) {
        String apiKey = "AIzaSyD3IJm-5PMPgKnmOKPqVtOvHzogioiJjys";
        Route route = null;
        // Lookup directions and compare the distance, save point with the shortest distance
        for (Map.Entry entry : nearbyPoints().entrySet()) {
            LatLng point = (LatLng) entry.getValue();
            String polygonName = entry.getKey().toString();
            Direction direction = GoogleDirection.withServerKey(apiKey)
                    .from(mOrigin)
                    .to(point)
                    .execute();

            if (direction.isOK()) {
                if (route == null) {
                    route = direction.getRouteList().get(0);
                    mClosestParking = polygonName;
                    mClosestCenter = point;
                } else {
                    Leg legFromShortest = route.getLegList().get(0);
                    Leg legToCheck = direction.getRouteList().get(0).getLegList().get(0);
                    int distanceFromShortest = Integer.parseInt(legFromShortest.getDistance().getValue());
                    int distanceToCheck = Integer.parseInt(legToCheck.getDistance().getValue());

                    if (distanceToCheck < distanceFromShortest) {
                        route = direction.getRouteList().get(0);
                        mClosestParking = polygonName;
                        mClosestCenter = point;
                    }
                }
            } else {
                Log.e(TAG, direction.getErrorMessage());
            }
        }
        return route;
    }

    @Override
    protected void onPostExecute(Route route) {
        super.onPostExecute(route);
        // Draw Polyline showing Directions to Parking
        Log.v(TAG, "Post Execute value route: " + route);
        ArrayList<LatLng> directionPoints = route.getLegList().get(0).getDirectionPoint();
        PolylineOptions lineOptions = DirectionConverter.createPolyline(mContext, directionPoints, 5, Color.RED);
        Polyline directionsLine = mMap.addPolyline(lineOptions);

        // First Hide all parking layers then display the nearest parking polygon
        for(KmlLayer layer : mLayers.values()) layer.removeLayerFromMap();
        // Get KmlPolygon of closest parking and get geometry object
        ArrayList<ArrayList<LatLng>> cp = mPolygons.get(mClosestParking).getGeometryObject();
        PolygonOptions polygonOptions = new PolygonOptions();
        // Iterate over geometry object adding LatLng points to polygon Options
        for (ArrayList<LatLng> cords : cp) {
            for (LatLng point : cords) {
                polygonOptions.add(new LatLng(point.latitude, point.longitude));
            }
        }
        polygonOptions
                .strokeColor(Color.RED)
                .fillColor(Color.CYAN);
        Polygon closestParkingPolygon = mMap.addPolygon(polygonOptions);

        // Create Marker for parking area. Use the polygons placemark name and description for the marker
        KmlPlacemark placemark = mMyKmlLayers.findPlaceMark(mClosestParking);
        MarkerOptions markerOptions = new MarkerOptions().position(mClosestCenter);
        if(placemark != null) {
            markerOptions
                .title(placemark.getProperty("name"))
                .snippet(placemark.getProperty("description"));
        }
        Marker closestParkingMarker = mMap.addMarker(markerOptions);

        // Move Camera to Parking Area
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mClosestCenter)
                .zoom(17)
                .tilt(45)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        // Passing the polygon to the activity
        mFinished.processFinish(closestParkingPolygon, directionsLine, closestParkingMarker);
    }

    // Find polygons that are within 1000 meters of the devices location
    private HashMap<String, LatLng> nearbyPoints() {
        HashMap<String, LatLng> nearby = new HashMap<>();
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();

        // For each Polygon find its center and add it to the hashmap
        for(Map.Entry entry : mPolygons.entrySet()) {
            String polygonName = entry.getKey().toString();
            KmlPolygon polygon = (KmlPolygon) entry.getValue();
            // For each point on the polygon
            for(int i = 0; i < polygon.getGeometryObject().size(); i++) {
                // Create bounds with points
                for (LatLng point : polygon.getGeometryObject().get(i)) {
                    LatLng cords = new LatLng(point.latitude, point.longitude);
                    boundsBuilder.include(cords);
                }
            }
            LatLngBounds bounds = boundsBuilder.build();
            Log.v(TAG, "Bounds for " + polygonName + bounds);
            LatLng center = bounds.getCenter();

            Log.v(TAG, polygonName + ": " + center);

            Location target = new Location("");
            target.setLatitude(center.latitude);
            target.setLongitude(center.longitude);

            nearby.put(polygonName, center);
        }
        return nearby;
    }
}

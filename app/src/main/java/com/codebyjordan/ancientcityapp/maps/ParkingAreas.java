package com.codebyjordan.ancientcityapp.maps;

import android.content.Context;
import android.graphics.Color;
import com.codebyjordan.ancientcityapp.maps.models.ParkingCoords;
import com.codebyjordan.ancientcityapp.maps.models.ParkingLocation;
import com.codebyjordan.ancientcityapp.maps.models.ParkingModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ParkingAreas {

    private Context mContext;
    private GoogleMap mMap;
    private List<Polyline> mStreets;
    private List<Polygon> mLots;
    private List<Polyline> mFreeStreets;
    private List<Polygon> mFreeLots;

    public ParkingAreas(Context context, GoogleMap map) {
        mContext = context;
        mMap = map;
        mStreets = new ArrayList<>();
        mLots = new ArrayList<>();
        mFreeStreets = new ArrayList<>();
        mFreeLots = new ArrayList<>();
    }

    public void drawLots() throws IOException {
        String json = getJsonFile();

        List<ParkingLocation> lots = ParkingModel.parseJson(json).getLots();

        for(ParkingLocation lot : lots) {
            drawLot(lot, mLots);
        }

    }

    private void drawLot(ParkingLocation lot, List<Polygon> list) {
        ParkingCoords[] coords = lot.getCoords();

        ParkingCoords start = coords[0];
        ParkingCoords second = coords[1];
        ParkingCoords third = coords[2];
        ParkingCoords end = coords[3];

        PolygonOptions rectOptions = new PolygonOptions()
                .add(new LatLng(start.getLat(), start.getLng()),
                        new LatLng(second.getLat(), second.getLng()),
                        new LatLng(third.getLat(), third.getLng()),
                        new LatLng(end.getLat(), end.getLng()))
                .strokeColor(Color.BLUE)
                .fillColor(Color.CYAN);

        Polygon poly = mMap.addPolygon(rectOptions);
        list.add(poly);
    }

    public void drawStreets() throws IOException {
        String json = getJsonFile();

        List<ParkingLocation> streets = ParkingModel.parseJson(json).getStreet();


        for(ParkingLocation street : streets) {
           drawStreet(street, mStreets);
        }
    }

    private void drawStreet(ParkingLocation street, List<Polyline> list) {
        ParkingCoords[] coords = street.getCoords();

        ParkingCoords start = coords[0];
        ParkingCoords end = coords[1];

        PolylineOptions lineOptions = new PolylineOptions()
                .add(new LatLng(start.getLat(), start.getLng()),
                        new LatLng(end.getLat(), end.getLng()))
                .width(5)
                .color(Color.CYAN);

        Polyline line = mMap.addPolyline(lineOptions);
        list.add(line);
    }

    public void drawFreeAfterFive() throws IOException {
        String json = getJsonFile();

        List<ParkingLocation> streets = ParkingModel.parseJson(json).getStreet();
        List<ParkingLocation> lots = ParkingModel.parseJson(json).getLots();

        for (ParkingLocation street : streets) {
            if(street.isFree()) drawStreet(street, mFreeStreets);
        }

        for (ParkingLocation lot : lots) {
            if (lot.isFree()) drawLot(lot, mFreeLots);
        }
    }

    public void clearStreets() {
        for (Polyline line : mStreets) line.remove();
    }

    public void clearLots() {
        for (Polygon poly : mLots) poly.remove();
    }

    public void clearFreeAfterFive() {
        for (Polyline line : mFreeStreets) line.remove();
        for (Polygon poly : mFreeLots) poly.remove();
    }

    public void clearAll() {
        for (Polyline line : mStreets) line.remove();
        for (Polygon poly : mLots) poly.remove();
        for (Polyline line : mFreeStreets) line.remove();
        for (Polygon poly : mFreeLots) poly.remove();
    }

    private String getJsonFile() throws IOException {
        InputStream is = mContext.getAssets().open("parking_lots.json");
        int size = is.available();
        byte[] buffer = new byte[size];

        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }
}

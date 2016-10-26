package com.codebyjordan.ancientcityapp.maps.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class ParkingModel{

    List<ParkingLocation> lots;
    List<ParkingLocation> street;

    public ParkingModel() {
        lots = new ArrayList<>();
        street = new ArrayList<>();
    }

    public static ParkingModel parseJson(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, ParkingModel.class);
    }

    public List<ParkingLocation> getLots() {
        return lots;
    }

    public List<ParkingLocation> getStreet() {
        return street;
    }
}

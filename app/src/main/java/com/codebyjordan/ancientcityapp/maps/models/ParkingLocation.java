package com.codebyjordan.ancientcityapp.maps.models;

public class ParkingLocation {

    String name;
    boolean free;
    ParkingCoords[] coords;

    public String getName() {
        return name;
    }

    public boolean isFree() {
        return free;
    }

    public ParkingCoords[] getCoords() {
        return coords;
    }
}

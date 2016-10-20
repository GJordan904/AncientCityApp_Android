package com.codebyjordan.ancientcityapp.yelp.models;

public class BusinessLocation {
    String city;
    String[] display_address;
    double geo_accuracy;
    String postal_code;
    String country_code;
    String[] address;
    BusinessLocationCoords coordinate;
    String state_code;

    public String getCity() {
        return city;
    }

    public String[] getDisplay_address() {
        return display_address;
    }

    public double getGeo_accuracy() {
        return geo_accuracy;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String[] getAddress() {
        return address;
    }

    public BusinessLocationCoords getCoordinate() {
        return coordinate;
    }

    public String getState_code() {
        return state_code;
    }
}

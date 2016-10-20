package com.codebyjordan.ancientcityapp.yelp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {

    int total;
    List<Business> businesses;

    public SearchResponse() {
        businesses = new ArrayList<Business>();
    }

    public static SearchResponse parseJson(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, SearchResponse.class);
    }

    public List<Business> getBusinesses() {
        return businesses;
    }

    public int getTotal() {
        return total;
    }
}

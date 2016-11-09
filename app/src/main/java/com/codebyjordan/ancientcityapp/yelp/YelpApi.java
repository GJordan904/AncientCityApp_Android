package com.codebyjordan.ancientcityapp.yelp;

import okhttp3.*;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;


public class YelpApi {

    private final String API_HOST = "api.yelp.com";
    private OkHttpClient mClient;
    private String mSearchTerm;
    private String mSearchId;
    private String[] mFilters;
    private int mFilterIndex;

    public YelpApi(int index, String searchTerm, String[] filters, OkHttpClient client){
        mSearchTerm = searchTerm;
        mFilterIndex = index;
        mFilters = filters;
        mClient = client;
    }

    public YelpApi(String id, OkHttpClient client) {
        mSearchId = id;
        mClient = client;
    }


    public Call createSearchCall() {
        // Build URL
        String SEARCH_PATH = "v2/search";
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(API_HOST)
                .addPathSegments(SEARCH_PATH)
                .addQueryParameter("term", mSearchTerm)
                .addQueryParameter("category_filter", mFilters[mFilterIndex])
                .addQueryParameter("location", "Saint+Augustine")
                .addQueryParameter("radius_filter", "8000")
                .build();

        // Build Request
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Return Call
        return mClient.newCall(request);
    }

    public Call createBusinessCall() {
        // Build URL
        String BUSINESS_PATH = "v2/business/";
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(API_HOST)
                .addPathSegments(BUSINESS_PATH)
                .addPathSegment(mSearchId)
                .build();

        // Build Request
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Return Call
        return mClient.newCall(request);
    }

}

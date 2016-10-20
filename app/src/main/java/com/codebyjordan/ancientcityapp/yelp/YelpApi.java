package com.codebyjordan.ancientcityapp.yelp;

import okhttp3.*;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;


public class YelpApi {

    private final String API_HOST = "api.yelp.com";
    private final String SEARCH_PATH = "v2/search";
    private final String BUSINESS_PATH = "v2/business/";

    private OkHttpClient mClient;
    private String mSearchTerm;
    private String mSearchLocation;
    private String mSearchId;

    public YelpApi(String searchTerm, String searchLocation){
        mSearchTerm = searchTerm;
        mSearchLocation = searchLocation;
    }

    public YelpApi(String id) {
        mSearchId = id;
    }

    public void searchArea(Callback callback) {
        createClient();

        // Build URL
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(API_HOST)
                .addPathSegments(SEARCH_PATH)
                .addQueryParameter("term", mSearchTerm)
                .addQueryParameter("location", mSearchLocation)
                .addQueryParameter("radius_filter", "8000")
                .build();

        // Build Request
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Make Call
        mClient.newCall(request).enqueue(callback);
    }

    public void lookupBusiness(Callback callback) {
        createClient();

        // Build URL
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

        // Make Call
        mClient.newCall(request).enqueue(callback);
    }

    private void createClient() {
        String CONSUMER_KEY = "iwj1fWu6Csx9mN4kEzX13A";
        String CONSUMER_SECRET = "oZwpXvfCyXIXoHLny0x3NzNNGRo";
        String TOKEN = "32hKpSefyBO87bmBC3ZMAeZOsFz8acBM";
        String TOKEN_SECRET = "adq4S57iQJcv8AGO0Nsb8FUc9s8";

        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(TOKEN, TOKEN_SECRET);

        mClient = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();
    }

}

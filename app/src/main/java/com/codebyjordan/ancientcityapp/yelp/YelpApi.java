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

    public YelpApi(int index, String searchTerm, String[] filters){
        mSearchTerm = searchTerm;
        mFilterIndex = index;
        mFilters = filters;
        mClient = createClient();
    }

    public YelpApi(String id) {
        mSearchId = id;
        mClient = createClient();
    }

    public OkHttpClient getClient() {
        return mClient;
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

    private OkHttpClient createClient() {
        String CONSUMER_KEY = "iwj1fWu6Csx9mN4kEzX13A";
        String CONSUMER_SECRET = "oZwpXvfCyXIXoHLny0x3NzNNGRo";
        String TOKEN = "32hKpSefyBO87bmBC3ZMAeZOsFz8acBM";
        String TOKEN_SECRET = "adq4S57iQJcv8AGO0Nsb8FUc9s8";

        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(TOKEN, TOKEN_SECRET);

        return new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();
    }
}

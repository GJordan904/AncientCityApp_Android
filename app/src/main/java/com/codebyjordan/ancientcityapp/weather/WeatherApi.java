package com.codebyjordan.ancientcityapp.weather;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class WeatherApi {

    private OkHttpClient mClient;

    public WeatherApi(OkHttpClient client) {
        mClient = client;
    }

    public Call createCall() {
        final String REQUEST_ALL_URL = "http://api.wunderground.com/api/aaf5056596784caa/forecast/forecast10day/hourly/tide/q/32084.json";
        Request request = new Request.Builder()
                .url(REQUEST_ALL_URL)
                .build();

        return mClient.newCall(request);
    }
}

package com.codebyjordan.ancientcityapp.okhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import okhttp3.*;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

import java.io.File;
import java.io.IOException;

public class OkHttpUtil {

    private static final int CACHE_SIZE = 10 * 1024 * 1024;
    private static final Interceptor REWRITE_CACHE_CONTROL = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());

            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + 60 * 60 * 24)
                    .build();
        }
    };

    public static OkHttpClient getOAuthClient(final Context context) {
        // Setup OAuth Client
        String CONSUMER_KEY = "iwj1fWu6Csx9mN4kEzX13A";
        String CONSUMER_SECRET = "oZwpXvfCyXIXoHLny0x3NzNNGRo";
        String TOKEN = "32hKpSefyBO87bmBC3ZMAeZOsFz8acBM";
        String TOKEN_SECRET = "adq4S57iQJcv8AGO0Nsb8FUc9s8";

        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(TOKEN, TOKEN_SECRET);

        // Setup Cache
        File cacheDirectory = new File(context.getFilesDir(), "yelp-cache");
        Cache cache = new Cache(cacheDirectory, CACHE_SIZE);

        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new SigningInterceptor(consumer))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if(isNetworkAvailable(context)) {
                            request = request.newBuilder()
                                    .header("Cache-Control", "public, max-age=" + 60 * 60 * 24)
                                    .build();
                        }else{
                            request = request
                                    .newBuilder()
                                    .header("Cache-Control", "public, only-if-cached")
                                    .build();
                        }
                        return chain.proceed(request);
                    }
                })
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL)
                .build();
    }

    public static OkHttpClient getWeatherClient(final Context context) {
        // Setup Cache
        File cacheDirectory = new File(context.getFilesDir(), "weather-cache");
        Cache cache = new Cache(cacheDirectory, CACHE_SIZE);

        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if(isNetworkAvailable(context)) {
                            request = request.newBuilder()
                                    .header("Cache-Control", "public, max-age=" + 60 * 60)
                                    .build();
                        }else{
                            request = request
                                    .newBuilder()
                                    .header("Cache-Control", "public, only-if-cached")
                                    .build();
                        }
                        return chain.proceed(request);
                    }
                })
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL)
                .build();
    }

    public static boolean isNetworkAvailable(Context context) {
        // TODO: Move this to another location
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }
}

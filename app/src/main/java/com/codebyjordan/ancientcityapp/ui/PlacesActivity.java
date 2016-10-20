package com.codebyjordan.ancientcityapp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.codebyjordan.ancientcityapp.MarginItemDecoration;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.adapters.PlacesAdapter;
import com.codebyjordan.ancientcityapp.yelp.YelpApi;
import com.codebyjordan.ancientcityapp.yelp.models.Business;
import com.codebyjordan.ancientcityapp.yelp.models.SearchResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PlacesAdapter mAdapter;
    private List<Business> mGridItems = new ArrayList<>();
    private String mSearchTerm;

    public static final String TAG = PlacesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        // Get Data passed from Main Activity and lookup Yelp Data
        Intent intent = getIntent();
        mSearchTerm = intent.getStringExtra(getString(R.string.key_term));
        lookupPlaces();

        // Create Toolbar and set as action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(myToolbar);
        // Get support ActionBar and set up button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Setup Grid View
        mRecyclerView = (RecyclerView) findViewById(R.id.gridView);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        // Set custom decoration for margins
        MarginItemDecoration decoration = new MarginItemDecoration(20);
        mRecyclerView.addItemDecoration(decoration);
        // Set adapter on view
        mAdapter = new PlacesAdapter(this, mGridItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void lookupPlaces() {
        // Call google
        YelpApi api = new YelpApi(mSearchTerm, "Saint+Augustine");
        api.searchArea(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        List<Business> resultList = SearchResponse.parseJson(response.body().string())
                                .getBusinesses();
                        for(Business result:resultList) mGridItems.add(result);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    Log.e("YelpAPI", "Exception caught: ", e);
                }
            }
        });
    }
}

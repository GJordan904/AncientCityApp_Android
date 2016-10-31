package com.codebyjordan.ancientcityapp.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.codebyjordan.ancientcityapp.decorators.MarginItemDecoration;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.adapters.PlacesRecyclerAdapter;
import com.codebyjordan.ancientcityapp.yelp.YelpApi;
import com.codebyjordan.ancientcityapp.yelp.models.Business;
import com.codebyjordan.ancientcityapp.yelp.models.SearchResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlacesFragment extends Fragment {

    private static final String TAG = "PlacesFragment";

    private PlacesRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private List<Business> mGridItems = new ArrayList<>();

    public static final String ARG_OBJECT = "object";
    public static final String ARG_TERM = "term";
    public static final String ARG_FILTERS = "filters";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places, container, false);
        view.setTag(TAG);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        // Setup Recycler View
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.placesRecycler);

        // Check if on phone or tablet and if landscape or portrait to setup the right LayoutManager
        boolean isPhone = getResources().getBoolean(R.bool.is_phone);
        boolean isLandscape = getResources().getBoolean(R.bool.is_landscape);

        if(isPhone && !isLandscape){
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        }else if(isPhone && isLandscape){
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }else if(!isPhone && !isLandscape) {
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }else if(!isPhone && isLandscape){
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        }

        // Set custom decoration for margins
        MarginItemDecoration decoration = new MarginItemDecoration(10);
        mRecyclerView.addItemDecoration(decoration);
        // Set adapter
        mAdapter = new PlacesRecyclerAdapter(view.getContext(), mGridItems);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        int index = args.getInt(ARG_OBJECT);
        String term = args.getString(ARG_TERM);
        String[] filters = args.getStringArray(ARG_FILTERS);

        lookupPlaces(index, term, filters);
    }

    private void lookupPlaces(int index, String term, String[] filters) {
        // Call google
        YelpApi api = new YelpApi(term, "Saint+Augustine", index, filters);
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
                        for(Business result:resultList) {
                            mGridItems.add(result);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                mProgressBar.setVisibility(View.GONE);
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

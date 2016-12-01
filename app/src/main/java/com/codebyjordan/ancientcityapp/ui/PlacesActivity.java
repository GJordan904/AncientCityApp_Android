package com.codebyjordan.ancientcityapp.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.adapters.PlacesRecyclerAdapter;
import com.codebyjordan.ancientcityapp.decorators.GridMarginDecorator;
import com.codebyjordan.ancientcityapp.okhttp.OkHttpUtil;
import com.codebyjordan.ancientcityapp.yelp.YelpApi;
import com.codebyjordan.ancientcityapp.yelp.models.Business;
import com.codebyjordan.ancientcityapp.yelp.models.SearchResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends AppCompatActivity {

    public static final String ARG_POSITION = "position";
    public static final String ARG_TERM = "term";
    public static final String ARG_FILTERS = "filters";
    private String mSearchTerm;
    private String[] mFilters;
    private String[] mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        // Get Data passed from Main Activity
        Intent intent = getIntent();
        mSearchTerm = intent.getStringExtra(getString(R.string.key_term));
        mFilters = intent.getStringArrayExtra(getString(R.string.key_filters));
        mTitles = intent.getStringArrayExtra(getString(R.string.key_fragment_titles));
        String activityTitle = intent.getStringExtra(getString(R.string.key_activity_title));

        // Set activity title
        setTitle(activityTitle);

        // Create Toolbar and set as action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(myToolbar);
        // Get support ActionBar and set up button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Setup Fragment Manager
        PlacesPagerAdapter adapter = new PlacesPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.placesPager);
        pager.setAdapter(adapter);

        // Setup Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.placesTabs);
        tabLayout.setupWithViewPager(pager);
    }
    //
    // Fragment State Pager Adapter
    //
    public class PlacesPagerAdapter extends FragmentStatePagerAdapter {

        public PlacesPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new PlacesFragment();
            Bundle args = new Bundle();

            args.putInt(ARG_POSITION, position);
            args.putString(ARG_TERM, mSearchTerm);
            args.putStringArray(ARG_FILTERS, mFilters);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

    //
    // Fragment
    //
    public static class PlacesFragment extends Fragment {

        private static final String TAG = "PlacesFragment";

        private PlacesRecyclerAdapter mAdapter;
        private Call mCall;
        private ProgressBar mProgressBar;
        private List<Business> mGridItems = new ArrayList<>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Bundle args = getArguments();
            int index = args.getInt(ARG_POSITION);
            String term = args.getString(ARG_TERM);
            String[] filters = args.getStringArray(ARG_FILTERS);

            lookupPlaces(index, term, filters);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_places, container, false);
            view.setTag(TAG);

            mProgressBar = (ProgressBar) view.findViewById(R.id.placesProgressBar);

            // Setup Recycler View
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.placesRecycler);

            // Check if on phone or tablet and if landscape or portrait to setup the right LayoutManager
            boolean isPhone = getResources().getBoolean(R.bool.is_phone);
            boolean isLandscape = getResources().getBoolean(R.bool.is_landscape);

            if(isPhone && !isLandscape){
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
            }else if(isPhone && isLandscape){
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            }else if(!isPhone && !isLandscape) {
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            }else if(!isPhone && isLandscape){
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
            }

            // Set custom decoration for margins
            GridMarginDecorator decoration = new GridMarginDecorator(10);
            recyclerView.addItemDecoration(decoration);
            // Set adapter
            mAdapter = new PlacesRecyclerAdapter(view.getContext(), mGridItems);
            recyclerView.setAdapter(mAdapter);

            return view;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            if(mCall != null) mCall.cancel();
        }

        private void lookupPlaces(int index, String term, String[] filters) {
            // Call google
            OkHttpClient client = OkHttpUtil.getOAuthClient(getActivity());
            YelpApi api = new YelpApi(index, term, filters, client);
            mCall = api.createSearchCall();
            mCall.enqueue(new Callback() {
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
}

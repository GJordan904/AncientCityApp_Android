package com.codebyjordan.ancientcityapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.custviews.DynamicHeightImageView;
import com.codebyjordan.ancientcityapp.maps.MapTool;
import com.codebyjordan.ancientcityapp.okhttp.OkHttpUtil;
import com.codebyjordan.ancientcityapp.picasso.FitToViewTransformation;
import com.codebyjordan.ancientcityapp.picasso.ResizableTarget;
import com.codebyjordan.ancientcityapp.yelp.YelpApi;
import com.codebyjordan.ancientcityapp.yelp.models.BusinessResponse;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import java.io.IOException;

public class PlaceDetailActivity extends AppCompatActivity {

    private static final String ARG_ID = "business_id";
    private static Context mContext;
    private static TabLayout mTabLayout;
    private static BusinessResponse mResult;
    private String mBusinessId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_detail);
        mContext = this;

        // Get Business ID passed from Places Activity
        Intent intent = getIntent();
        mBusinessId = intent.getStringExtra(getString(R.string.key_business_id));

        // Create Toolbar and set as action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(myToolbar);
        // Get support ActionBar and set up button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Setup Fragment Manager
        DetailPagerAdapter adapter = new DetailPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.placesDetailsPager);
        pager.setAdapter(adapter);

        // Setup Tab Layout
        mTabLayout = (TabLayout) findViewById(R.id.placesDetailsTabs);
        mTabLayout.setupWithViewPager(pager);
    }

    //
    // Fragment State Page Adapter
    //
    public class DetailPagerAdapter extends FragmentStatePagerAdapter {

        public DetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position) {
                case 0:
                    fragment = new PlaceDetailFragment();
                    Bundle args = new Bundle();
                    args.putString(ARG_ID, mBusinessId);
                    fragment.setArguments(args);
                    break;
                case 1:
                    fragment = new PlaceDetailMap();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            CharSequence title = "";
            switch (position) {
                case 0:
                    title =  getString(R.string.pager_title_details);
                    break;
                case 1:
                    title = getString(R.string.pager_title_map);
                    break;

            }
            return title;
        }
    }

    //
    // Detail Fragment
    //
    public static class PlaceDetailFragment extends Fragment implements View.OnClickListener {

        private String mBusinessId;
        private Call mCall;
        private ProgressBar mProgressBar;
        private DynamicHeightImageView mPlaceImage;
        private TextView mPlaceName;
        private TextView mPlacePhone;
        private TextView mPlaceAddress;
        private TextView mPlaceSnippet;
        private TextView mPlaceHours;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Bundle args = getArguments();
            mBusinessId = args.getString(ARG_ID);

            getDetails();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_place_detail, container, false);

            mProgressBar = (ProgressBar) view.findViewById(R.id.detailProgressBar);
            mPlaceImage = (DynamicHeightImageView) view.findViewById(R.id.placeDetailsImage);
            mPlaceName = (TextView) view.findViewById(R.id.placeName);
            mPlacePhone = (TextView) view.findViewById(R.id.phoneText);
            mPlaceAddress = (TextView) view.findViewById(R.id.addressText);
            mPlaceSnippet = (TextView) view.findViewById(R.id.descriptionText);

            mPlacePhone.setOnClickListener(this);
            mPlaceAddress.setOnClickListener(this);

            return view;
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.phoneText:
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + mResult.getPhone()));
                    mContext.startActivity(callIntent);
                    break;
                case R.id.addressText:
                    TabLayout.Tab tab = mTabLayout.getTabAt(1);
                    if (tab != null) {
                        tab.select();
                    }
            }
        }

        private void getDetails() {
            OkHttpClient client = OkHttpUtil.getOAuthClient(getActivity());
            YelpApi api = new YelpApi(mBusinessId, client);
            mCall = api.createBusinessCall();
            mCall.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            final BusinessResponse result = BusinessResponse.parseJson(response.body().string());

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mResult = result;
                                    // Set Text Fields
                                    mPlaceName.setText(result.getName());
                                    mPlaceAddress.setText(result.getLocation().getDisplay_address()[0]);
                                    mPlacePhone.setText(result.formattedPhone());
                                    mPlaceSnippet.setText(result.getSnippet_text());
                                    // Load Images
                                    Target target = new ResizableTarget(mPlaceImage);
                                    mPlaceImage.setTag(target);

                                    Picasso.with(mContext)
                                            .load(result.getImage_url())
                                            .placeholder(R.drawable.cast_album_art_placeholder)
                                            .error(R.drawable.item_icon_parking)
                                            .transform(new FitToViewTransformation(mPlaceImage))
                                            .into(target);
                                    // Set Activity Title
                                    getActivity().setTitle(result.getName());
                                    // Hide Progress Bar
                                    mProgressBar.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            mProgressBar.setVisibility(View.GONE);
                            throw new IOException("Unexpected code " + response);
                        }
                    } catch (IOException e) {
                        Log.e("YelpAPI", "Exception caught: ", e);
                    }
                }
            });
        }
    }

    //
    // Map Fragment
    //
    public static class PlaceDetailMap extends Fragment implements OnMapReadyCallback {

        private static GoogleMap mMap;
        private static MapTool mapTool;

        public PlaceDetailMap(){}

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_place_map, container, false);

            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.detailsMap);
            mapFragment.getMapAsync(this);

            return view;
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mapTool = new MapTool(mMap, getActivity());

            mapTool.setupUi();
            mapTool.styleMap();
        }

        @Override
        public void setMenuVisibility(boolean menuVisible) {
            super.setMenuVisibility(menuVisible);
            if(menuVisible) createMarker();
        }

        public void createMarker(){
            LatLng position = new LatLng(mResult.getLocation().getCoordinate().getLatitude(), mResult.getLocation().getCoordinate().getLongitude());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(position)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .title(mResult.getName());

            Marker placeMarker = mMap.addMarker(markerOptions);

            // Move Camera
            mapTool.moveCamera(position, 15, 45);
        }
    }
}

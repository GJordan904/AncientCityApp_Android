package com.codebyjordan.ancientcityapp.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.adapters.PlacesFragmentPager;

public class FoodDrinkActivity extends AppCompatActivity {

    public String[] fragmentTitles() {
        return new String[]{
                "All",
                "American",
                "Breakfast",
                "French",
                "Italian",
                "Spanish"
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        // Get Data passed from Main Activity to pass to fragment
        Intent intent = getIntent();
        String searchTerm = intent.getStringExtra(getString(R.string.key_term));
        String[] searchFilters = intent.getStringArrayExtra(getString(R.string.key_filters));

        // Create Toolbar and set as action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(myToolbar);
        // Get support ActionBar and set up button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Setup Fragment Manager
        PlacesFragmentPager mPlacesFragmentPager = new PlacesFragmentPager(getSupportFragmentManager(),
                searchTerm, searchFilters, fragmentTitles());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.placesPager);
        mViewPager.setAdapter(mPlacesFragmentPager);

        // Setup Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
    }
}

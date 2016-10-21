package com.codebyjordan.ancientcityapp.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.codebyjordan.ancientcityapp.ui.PlacesFragment;

public class PlacesFragmentPager extends FragmentStatePagerAdapter {

    private String mSearchTerm;
    private String[] mTitles;
    private String[] mFilters;

    public PlacesFragmentPager(FragmentManager fm, String searchTerm,
                               String[] filters, String[] titles) {
        super(fm);
        mSearchTerm = searchTerm;
        mTitles = titles;
        mFilters = filters;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new PlacesFragment();
        Bundle args = new Bundle();

        args.putInt(PlacesFragment.ARG_OBJECT, position);
        args.putString(PlacesFragment.ARG_TERM, mSearchTerm);
        args.putStringArray(PlacesFragment.ARG_FILTERS, mFilters);
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

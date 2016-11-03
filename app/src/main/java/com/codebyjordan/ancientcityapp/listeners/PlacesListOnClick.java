package com.codebyjordan.ancientcityapp.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.codebyjordan.ancientcityapp.R;

public class PlacesListOnClick implements View.OnClickListener {

    private final String[][] SEARCH_PARAMS = {
            {
                    "restaurants",
                    "hotels",
                    "arts"
            },
            {
                    "",
                    "tradamerican",
                    "breakfast_brunch",
                    "french",
                    "italian",
                    "spanish"

            },
            {
                    "",
                    "hotels",
                    "bedbreakfast",
                    "campgrounds,rvparks"

            },
            {
                    "",
                    "galleries",
                    "museums",
                    "hauntedhouses"
            }};
    public final String[][] FRAGMENT_TITLES = {
            {
                    "All",
                    "American",
                    "Breakfast",
                    "French",
                    "Italian",
                    "Spanish"
            },
            {
                    "Hotels",
                    "Bed & Breakfast",
                    "Campgrounds"
            },
            {
                    "All",
                    "Art Galleries",
                    "Museums",
                    "Haunted Houses"
            }
    };
    public final String[] ACTIVITY_TITLES = {
            "Restaurants",
            "Lodging",
            "Attractions"
    };

    private Class mActivityClass;
    private Context mContext;
    private int mIndex;

    public PlacesListOnClick(Context context, Class ActivityClass, int index) {
        mContext = context;
        mActivityClass = ActivityClass;
        mIndex = index;
    }

    @Override
    public void onClick(View v) {
        startAct();
    }

    private void startAct() {
        Intent intent = new Intent(mContext, mActivityClass);
        intent.putExtra(mContext.getString(R.string.key_term), SEARCH_PARAMS[0][mIndex]);
        intent.putExtra(mContext.getString(R.string.key_filters), SEARCH_PARAMS[mIndex + 1]);
        intent.putExtra(mContext.getString(R.string.key_activity_title), ACTIVITY_TITLES[mIndex]);
        intent.putExtra(mContext.getString(R.string.key_fragment_titles), FRAGMENT_TITLES[mIndex]);
        mContext.startActivity(intent);
    }
}

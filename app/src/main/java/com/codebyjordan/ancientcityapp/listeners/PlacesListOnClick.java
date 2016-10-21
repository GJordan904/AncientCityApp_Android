package com.codebyjordan.ancientcityapp.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.codebyjordan.ancientcityapp.R;

public class PlacesListOnClick implements View.OnClickListener {

    private Class mActivityClass;
    private Context mContext;
    private int mIndex;
    private String[][] mSearchParam = {
            {
                    "restaurants",
                    "hotels",
                    "attractions"
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
                    ""
            }};

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
        intent.putExtra(mContext.getString(R.string.key_term), mSearchParam[0][mIndex]);
        intent.putExtra(mContext.getString(R.string.key_filters), mSearchParam[mIndex + 1]);
        mContext.startActivity(intent);
    }
}

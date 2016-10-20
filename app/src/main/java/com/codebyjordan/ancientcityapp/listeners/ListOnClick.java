package com.codebyjordan.ancientcityapp.listeners;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import com.codebyjordan.ancientcityapp.R;

public class ListOnClick implements View.OnClickListener {

    private Class mActivityClass;
    private Context mContext;
    private int mIndex;
    private String[] mSearchTerms = {
        "restaurants",
        "bars",
        "lodging",
        "attractions"
    };

    public ListOnClick(Context context, Class ActivityClass, int index) {
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
        intent.putExtra(mContext.getString(R.string.key_term), mSearchTerms[mIndex]);
        mContext.startActivity(intent);
    }
}

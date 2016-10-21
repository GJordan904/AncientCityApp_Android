package com.codebyjordan.ancientcityapp.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class StartActivityOnClick implements View.OnClickListener {

    private Class mActivityClass;
    private Context mContext;

    public StartActivityOnClick(Context context, Class activityClass) {
        mActivityClass = activityClass;
        mContext = context;
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, mActivityClass);
        mContext.startActivity(intent);
    }
}

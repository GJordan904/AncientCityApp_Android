package com.codebyjordan.ancientcityapp.weather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import com.codebyjordan.ancientcityapp.R;

public class WeatherHelper {

    private Context mContext;

    public WeatherHelper(Context context) {
        mContext = context;
    }

    public Drawable getConditionIcon(String condition) {
        Drawable icon;
        switch(condition) {
            case "chancerain":
                icon = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.icon_chance_rain, null);
                break;
            case "chancetstorms":
                icon = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.icon_chance_tstorms, null);
                break;
            case "clear":case"sunny":
                icon = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.icon_clear_day, null);
                break;
            case"cloudy":case"fog":
                icon = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.icon_cloudy, null);
                break;
            case"mostlycloudy":case"partlysunny":
                icon = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.icon_mostly_cloudy_day, null);
                break;
            case"mostlysunny":case"partlycloudy":
                icon = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.icon_mostly_sunny_day, null);
                break;
            case"rain":
                icon = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.icon_rain_day, null);
                break;
            case"tstorms":
                icon = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.icon_tstorms, null);
                break;
            default:
                icon = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.icon_clear_day, null);
                break;
        }
        return icon;
    }
}

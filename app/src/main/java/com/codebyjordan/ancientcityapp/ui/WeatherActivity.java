package com.codebyjordan.ancientcityapp.ui;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.okhttp.OkHttpUtil;
import com.codebyjordan.ancientcityapp.weather.WeatherApi;
import com.codebyjordan.ancientcityapp.weather.models.*;
import com.plattysoft.leonids.ParticleSystem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    private static AllWeather mWeather;
    private static String mCondition;
    private static int mForecastHour;
    private static ProgressBar mProgressBar;
    private static RelativeLayout mMainRelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Create Toolbar and set as action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(myToolbar);
        // Get support ActionBar and set up button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // Setup Fragment Manager
        WeatherPagerAdapter adapter = new WeatherPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.weatherPager);
        pager.setAdapter(adapter);

        // Setup Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.weatherTabs);
        tabLayout.setupWithViewPager(pager);

        // Setup View Variables
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMainRelative = (RelativeLayout) findViewById(R.id.mainRelative);

    }

    public class WeatherPagerAdapter extends FragmentStatePagerAdapter{

        public WeatherPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position) {
                case 0:
                    fragment = new ForecastFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = "Forecast";
                    break;
                case 1:
                    title = "Extended";
                    break;
            }
            return title;
        }
    }

    public static class ForecastFragment extends Fragment {

        private Call mCall;
        private TextView mTempText;
        private TextView mConditionText;
        private TextView mFeelsLikeText;
        private TextView mPrecipChanceText;
        private TextView mHighLowText;
        private TextView mWindText;
        private ImageView mTomImage;
        private TextView mTomText;
        private TextView mTomHigh;
        private TextView mTomLow;
        private ImageView mDayAfterImage;
        private TextView mDayAfterText;
        private TextView mDayAfterHigh;
        private TextView mDayAfterLow;
        private ImageView mThirdDayImage;
        private TextView mThirdDayText;
        private TextView mThirdDayHigh;
        private TextView mThirdDayLow;
        private TextView mForecastText;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

            // Today's Conditions
            mTempText = (TextView) view.findViewById(R.id.tempText);
            mConditionText = (TextView) view.findViewById(R.id.conditionText);
            mFeelsLikeText = (TextView) view.findViewById(R.id.feelsLikeText);
            mPrecipChanceText = (TextView) view.findViewById(R.id.chancePrecipText);
            mHighLowText = (TextView) view.findViewById(R.id.highLowText);
            mWindText = (TextView) view.findViewById(R.id.windText);

            // Tomorrow's Conditions
            mTomImage = (ImageView) view.findViewById(R.id.tomImage);
            mTomText = (TextView) view.findViewById(R.id.tomText);
            mTomHigh = (TextView) view.findViewById(R.id.tomHigh);
            mTomLow = (TextView) view.findViewById(R.id.tomLow);

            // Day after Conditions
            mDayAfterImage = (ImageView) view.findViewById(R.id.dayAfterImage);
            mDayAfterText = (TextView) view.findViewById(R.id.dayAfterText);
            mDayAfterHigh = (TextView) view.findViewById(R.id.dayAfterHigh);
            mDayAfterLow = (TextView) view.findViewById(R.id.dayAfterLow);

            // Third Day Conditions
            mThirdDayImage = (ImageView) view.findViewById(R.id.thirdDayImage);
            mThirdDayText = (TextView) view.findViewById(R.id.thirdDayText);
            mThirdDayHigh = (TextView) view.findViewById(R.id.thirdDayHigh);
            mThirdDayLow = (TextView) view.findViewById(R.id.thirdDayLow);

            // Today's Text
            mForecastText = (TextView) view.findViewById(R.id.forecastText);

            return view;
        }

        @Override
        public void onStart() {
            super.onStart();
            checkWeather();
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            if(mCall != null) mCall.cancel();
        }

        private void checkWeather() {
            OkHttpClient client = OkHttpUtil.getWeatherClient(getActivity());
            mCall = new WeatherApi(client).createCall();
            mCall.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("WeatherActivity", e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            mWeather = AllWeather.parseJson(response.body().string());
                            mCondition = mWeather.getHourlyForecast().get(0).getIcon();
                            mForecastHour = Integer.parseInt(mWeather.getHourlyForecast().get(0).getForecastTime().getHour());

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setBackground();
                                    setRainAnimation();
                                    setUiElements();
                                    mProgressBar.setVisibility(View.GONE);
                                }
                            });

                        }else {
                            throw new IOException("Unexpected code " + response);
                        }
                    } catch (IOException e) {
                        Log.e("WeatherAPI", "Exception caught: ", e);
                    }
                }
            });
        }

        private void setBackground() {
            // Set Background according to weather
            if(mForecastHour >= 6 && mForecastHour <= 19) {
                switch(mCondition) {
                    case "clear":case "sunny":
                        mMainRelative.setBackgroundResource(R.drawable.back_clear_day);
                        break;
                    case "cloudy":case "rain":
                    case "tstorms":case "fog":
                    case "chancerain":case "chancetstorms":
                        mMainRelative.setBackgroundResource(R.drawable.back_cloudy_day);
                        break;
                    case "mostlycloudy":case "mostlysunny":
                    case "partlycloudy":case "partlysunny":
                        mMainRelative.setBackgroundResource(R.drawable.back_partly_day);
                        break;
                    default:
                        mMainRelative.setBackgroundResource(R.drawable.back_partly_day);
                }
            }else {
                switch(mCondition) {
                    case "clear":case "sunny":
                        mMainRelative.setBackgroundResource(R.drawable.back_clear_night);
                        break;
                    case "cloudy":case "rain":
                    case "tstorms":case "fog":
                    case "chancerain":case "chancetstorms":
                        mMainRelative.setBackgroundResource(R.drawable.back_cloudy_night);
                        break;
                    case "mostlycloudy":case "mostlysunny":
                    case "partlycloudy":case "partlysunny":
                        mMainRelative.setBackgroundResource(R.drawable.back_partly_night);
                        break;
                    default:
                        mMainRelative.setBackgroundResource(R.drawable.back_partly_night);
                }
            }
        }

        private void setRainAnimation() {
            // Show rain if raining
            switch (mCondition) {
                case "rain":case "tstorms":
                case "nt_rain":case "nt_tstorms":
                    // Get Screen Width to Find corner coordinates
                    Display mdisp = getActivity().getWindowManager().getDefaultDisplay();
                    Point mdispSize = new Point();
                    mdisp.getSize(mdispSize);
                    int maxX = mdispSize.x;

                    // Create Rain from Left
                    new ParticleSystem(getActivity(), 100, R.drawable.raindrop, 1000)
                            .setSpeedModuleAndAngleRange(.1f, .9f, -45, 90)
                            .setAcceleration(.005f, 45)
                            .emit(0, 0, 20);

                    // Create Rain from right
                    new ParticleSystem(getActivity(), 100, R.drawable.raindrop, 1000)
                            .setSpeedModuleAndAngleRange(.1f, .9f, -45, 90)
                            .setAcceleration(.005f, -45)
                            .emit(maxX, 0, 20);
                    break;
            }
        }

        private void setUiElements() {
            HourlyForecast rightNow = mWeather.getHourlyForecast().get(0);
            List<ForecastDay> txtForecast = mWeather.getForecast().getTxtForecast().getForecastday();
            SimpleForecastDay today = mWeather.getForecast().getSimpleforecast().getForecastday().get(0);
            SimpleForecastDay tomorrow = mWeather.getForecast().getSimpleforecast().getForecastday().get(1);
            SimpleForecastDay dayAfter = mWeather.getForecast().getSimpleforecast().getForecastday().get(2);
            SimpleForecastDay thirdDay = mWeather.getForecast().getSimpleforecast().getForecastday().get(3);

            // Set Today's Conditions
            mTempText.setText(rightNow.getTemp().getEnglish() + "\u00B0");
            mConditionText.setText(rightNow.getCondition());
            mFeelsLikeText.setText("Feels like " + rightNow.getFeelslike().getEnglish() + "\u00B0");
            mPrecipChanceText.setText(rightNow.getPop() + "%");
            mHighLowText.setText(today.getHigh().getFahrenheit() + "\u00B0 - " + today.getLow().getFahrenheit() + "\u00B0");
            mWindText.setText(today.getAvewind().getMph() + "mph");

            // Set Tomorrows Conditions
            mTomImage.setBackground(getConditionIcon(tomorrow.getIcon()));
            mTomText.setText(tomorrow.getDate().getWeekday());
            mTomHigh.setText(tomorrow.getHigh().getFahrenheit() + "\u00B0");
            mTomLow.setText(tomorrow.getLow().getFahrenheit() + "\u00B0");

            // Set Day After Tomorrow Conditions
            mDayAfterImage.setBackground(getConditionIcon(dayAfter.getIcon()));
            mDayAfterText.setText(dayAfter.getDate().getWeekday());
            mDayAfterHigh.setText(dayAfter.getHigh().getFahrenheit() + "\u00B0");
            mDayAfterLow.setText(dayAfter.getLow().getFahrenheit() + "\u00B0");

            // Set Third Day Conditions
            mThirdDayImage.setBackground(getConditionIcon(thirdDay.getIcon()));
            mThirdDayText.setText(thirdDay.getDate().getWeekday());
            mThirdDayHigh.setText(thirdDay.getHigh().getFahrenheit() + "\u00B0");
            mThirdDayLow.setText(thirdDay.getLow().getFahrenheit() + "\u00B0");

            // Set Forecast Text
            if(mForecastHour >= 6 && mForecastHour <= 19) {
                mForecastText.setText(txtForecast.get(0).getFcttext());
            }else {
                mForecastText.setText(txtForecast.get(1).getFcttext());
            }
        }

        private Drawable getConditionIcon(String condition) {
            Drawable icon;
            switch(condition) {
                case "chancerain":
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_chance_rain, null);
                    break;
                case "chancetstorms":
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_chance_tstorms, null);
                    break;
                case "clear":case"sunny":
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_clear_day, null);
                    break;
                case"cloudy":case"fog":
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_cloudy, null);
                    break;
                case"mostlycloudy":case"partlysunny":
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_mostly_cloudy_day, null);
                    break;
                case"mostlysunny":case"partlycloudy":
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_mostly_sunny_day, null);
                    break;
                case"rain":
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_rain_day, null);
                    break;
                case"tstorms":
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_tstorms, null);
                    break;
                default:
                    icon = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_clear_day, null);
                    break;
            }
            return icon;
        }
    }
}

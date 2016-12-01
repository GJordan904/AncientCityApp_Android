package com.codebyjordan.ancientcityapp.ui;

import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.codebyjordan.ancientcityapp.adapters.TenDayRecyclerAdapter;
import com.codebyjordan.ancientcityapp.okhttp.OkHttpUtil;
import com.codebyjordan.ancientcityapp.weather.WeatherApi;
import com.codebyjordan.ancientcityapp.weather.WeatherHelper;
import com.codebyjordan.ancientcityapp.weather.models.*;
import com.plattysoft.leonids.ParticleSystem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    private static AllWeather weather;
    private static List<SimpleForecastDay> simpleForecast = new ArrayList<>();
    private static String condition;
    private static int mForecastHour;
    private static ProgressBar progressBar;
    private static ViewPager viewPager;
    private static TenDayRecyclerAdapter adapter;

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
        viewPager = (ViewPager) findViewById(R.id.weatherPager);
        viewPager.setAdapter(adapter);

        // Setup Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.weatherTabs);
        tabLayout.setupWithViewPager(viewPager);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


    }
    //
    // Pager Adapter
    //
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
                case 1:
                    fragment = new TenDayFragment();
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
    //
    // Forecast Fragment
    //
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
                            weather = AllWeather.parseJson(response.body().string());
                            condition = weather.getHourlyForecast().get(0).getIcon();
                            List<SimpleForecastDay> forecast = weather.getForecast().getSimpleforecast().getForecastday();
                            for(SimpleForecastDay day : forecast) simpleForecast.add(day);
                            mForecastHour = Integer.parseInt(weather.getHourlyForecast().get(0).getForecastTime().getHour());

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setBackground();
                                    setRainAnimation();
                                    setUiElements();
                                    adapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
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
                switch(condition) {
                    case "clear":case "sunny":
                        viewPager.setBackgroundResource(R.drawable.back_clear_day);
                        break;
                    case "cloudy":case "rain":
                    case "tstorms":case "fog":
                    case "chancerain":case "chancetstorms":
                        viewPager.setBackgroundResource(R.drawable.back_cloudy_day);
                        break;
                    case "mostlycloudy":case "mostlysunny":
                    case "partlycloudy":case "partlysunny":
                        viewPager.setBackgroundResource(R.drawable.back_partly_day);
                        break;
                    default:
                        viewPager.setBackgroundResource(R.drawable.back_partly_day);
                }
            }else {
                switch(condition) {
                    case "clear":case "sunny":
                        viewPager.setBackgroundResource(R.drawable.back_clear_night);
                        break;
                    case "cloudy":case "rain":
                    case "tstorms":case "fog":
                    case "chancerain":case "chancetstorms":
                        viewPager.setBackgroundResource(R.drawable.back_cloudy_night);
                        break;
                    case "mostlycloudy":case "mostlysunny":
                    case "partlycloudy":case "partlysunny":
                        viewPager.setBackgroundResource(R.drawable.back_partly_night);
                        break;
                    default:
                        viewPager.setBackgroundResource(R.drawable.back_partly_night);
                }
            }
        }

        private void setRainAnimation() {
            // Show rain if raining
            switch (condition) {
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
            WeatherHelper helper = new WeatherHelper(getActivity());
            HourlyForecast rightNow = weather.getHourlyForecast().get(0);
            List<ForecastDay> txtForecast = weather.getForecast().getTxtForecast().getForecastday();
            SimpleForecastDay today = simpleForecast.get(0);
            SimpleForecastDay tomorrow = simpleForecast.get(1);
            SimpleForecastDay dayAfter = simpleForecast.get(2);
            SimpleForecastDay thirdDay = simpleForecast.get(3);

            // Set Today's Conditions
            mTempText.setText(rightNow.getTemp().getEnglish() + "\u00B0");
            mConditionText.setText(rightNow.getCondition());
            mFeelsLikeText.setText("Feels like " + rightNow.getFeelslike().getEnglish() + "\u00B0");
            mPrecipChanceText.setText(rightNow.getPop() + "%");
            mHighLowText.setText(today.getHigh().getFahrenheit() + "\u00B0/" + today.getLow().getFahrenheit() + "\u00B0");
            mWindText.setText(today.getAvewind().getMph() + "mph");

            // Set Tomorrows Conditions
            mTomImage.setBackground(helper.getConditionIcon(tomorrow.getIcon()));
            mTomText.setText(tomorrow.getDate().getWeekday());
            mTomHigh.setText(tomorrow.getHigh().getFahrenheit() + "\u00B0");
            mTomLow.setText(tomorrow.getLow().getFahrenheit() + "\u00B0");

            // Set Day After Tomorrow Conditions
            mDayAfterImage.setBackground(helper.getConditionIcon(dayAfter.getIcon()));
            mDayAfterText.setText(dayAfter.getDate().getWeekday());
            mDayAfterHigh.setText(dayAfter.getHigh().getFahrenheit() + "\u00B0");
            mDayAfterLow.setText(dayAfter.getLow().getFahrenheit() + "\u00B0");

            // Set Third Day Conditions
            mThirdDayImage.setBackground(helper.getConditionIcon(thirdDay.getIcon()));
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
    }
    //
    // Ten Day Forecast Fragment
    //
    public static class TenDayFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_weather_tenday, container, false);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.tendayRecycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

           // ListMarginDecoration decoration = new ListMarginDecoration(20);
           // recyclerView.addItemDecoration(decoration);

            adapter = new TenDayRecyclerAdapter(getActivity(), simpleForecast);
            recyclerView.setAdapter(adapter);


            return view;
        }
    }
}

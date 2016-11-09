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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.okhttp.OkHttpUtil;
import com.codebyjordan.ancientcityapp.weather.WeatherApi;
import com.codebyjordan.ancientcityapp.weather.models.AllWeather;
import com.plattysoft.leonids.ParticleSystem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;

public class WeatherActivity extends AppCompatActivity {

    private Call mCall;

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
        
        checkWeather();
    }

    private void checkWeather() {
        OkHttpClient client = OkHttpUtil.getWeatherClient(this);
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
                        AllWeather weather = AllWeather.parseJson(response.body().string());
                    }else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    Log.e("YelpAPI", "Exception caught: ", e);
                }
            }
        });
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

        private RelativeLayout mMainLayout;
        private TextView mTempText;
        private TextView mConditionText;
        private TextView mFeelsLikeText;


        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

            mMainLayout = (RelativeLayout)view.findViewById(R.id.mainRelative);

            return view;
        }

        @Override
        public void onStart() {
            super.onStart();
            // Get Screen Width to Find corner coordinates
            Display mdisp = getActivity().getWindowManager().getDefaultDisplay();
            Point mdispSize = new Point();
            mdisp.getSize(mdispSize);
            int maxX = mdispSize.x;
            int maxY = mdispSize.y;

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
        }
    }
}

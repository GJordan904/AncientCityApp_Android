package com.codebyjordan.ancientcityapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TableRow;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.listeners.PlacesListOnClick;
import com.codebyjordan.ancientcityapp.listeners.StartActivityOnClick;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create Toolbar and set as action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(myToolbar);

        // Setup Main Activity List
        TableRow restaurants = (TableRow) findViewById(R.id.rowRestaurants);
        TableRow lodging = (TableRow) findViewById(R.id.rowLodging);
        TableRow attractions = (TableRow) findViewById(R.id.rowAttractions);
        TableRow parking = (TableRow) findViewById(R.id.rowParking);
        TableRow events = (TableRow) findViewById(R.id.rowEvents);
        TableRow weather = (TableRow) findViewById(R.id.rowWeather);
        TableRow map = (TableRow) findViewById(R.id.rowMap);
        // Attach click listeners
        restaurants.setOnClickListener(new PlacesListOnClick(MainActivity.this, PlacesActivity.class, 0));
        lodging.setOnClickListener(new PlacesListOnClick(MainActivity.this, PlacesActivity.class, 1));
        attractions.setOnClickListener(new PlacesListOnClick(MainActivity.this, PlacesActivity.class, 2));

        parking.setOnClickListener(new StartActivityOnClick(MainActivity.this, ParkingActivity.class));
        weather.setOnClickListener(new StartActivityOnClick(MainActivity.this, WeatherActivity.class));
        map.setOnClickListener(new StartActivityOnClick(MainActivity.this, MapActivity.class));
    }
}

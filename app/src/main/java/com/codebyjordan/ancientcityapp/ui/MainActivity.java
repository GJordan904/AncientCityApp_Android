package com.codebyjordan.ancientcityapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TableRow;
import com.codebyjordan.ancientcityapp.R;
import com.codebyjordan.ancientcityapp.listeners.ListOnClick;

import java.util.ArrayList;


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
        TableRow[] placesList = {
                (TableRow) findViewById(R.id.rowRestaurants),
                (TableRow) findViewById(R.id.rowBars),
                (TableRow) findViewById(R.id.rowLodging),
                (TableRow) findViewById(R.id.rowAttractions)
        };
        TableRow parking = (TableRow) findViewById(R.id.rowParking);
        TableRow events = (TableRow) findViewById(R.id.rowEvents);
        TableRow weather = (TableRow) findViewById(R.id.rowWeather);
        TableRow map = (TableRow) findViewById(R.id.rowMap);

        // Attach click listeners to list items that redirect to PlacesActivity
        for(int i = 0; i<placesList.length; i++){
            placesList[i].setOnClickListener(new ListOnClick(MainActivity.this, PlacesActivity.class, i));
        }

        // End Main Activity List Setup
    }
}

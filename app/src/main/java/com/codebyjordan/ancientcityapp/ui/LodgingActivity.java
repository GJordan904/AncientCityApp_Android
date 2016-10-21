package com.codebyjordan.ancientcityapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.codebyjordan.ancientcityapp.R;

public class LodgingActivity extends FoodDrinkActivity {

    @Override
    public String[] fragmentTitles() {
        return new String[] {
                "Hotels",
                "Bed & Breakfast",
                "Campgrounds"
        };
    }

}

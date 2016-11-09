
package com.codebyjordan.ancientcityapp.weather.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Simpleforecast {

    @SerializedName("forecastday")
    @Expose
    private List<SimpleForecastDay> forecastday = new ArrayList<SimpleForecastDay>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Simpleforecast() {
    }

    /**
     * 
     * @param forecastday
     */
    public Simpleforecast(List<SimpleForecastDay> forecastday) {
        this.forecastday = forecastday;
    }

    /**
     * 
     * @return
     *     The forecastday
     */
    public List<SimpleForecastDay> getForecastday() {
        return forecastday;
    }

    /**
     * 
     * @param forecastday
     *     The forecastday
     */
    public void setForecastday(List<SimpleForecastDay> forecastday) {
        this.forecastday = forecastday;
    }

}

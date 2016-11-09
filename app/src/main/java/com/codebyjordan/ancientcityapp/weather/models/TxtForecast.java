
package com.codebyjordan.ancientcityapp.weather.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TxtForecast {

    @SerializedName("forecastday")
    @Expose
    private List<ForecastDay> forecastday = new ArrayList<ForecastDay>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public TxtForecast() {
    }

    /**
     * 
     * @param forecastDay
     */
    public TxtForecast(List<ForecastDay> forecastDay) {
        this.forecastday = forecastDay;
    }

    /**
     * 
     * @return
     *     The forecastday
     */
    public List<ForecastDay> getForecastday() {
        return forecastday;
    }

    /**
     * 
     * @param forecastDay
     *     The forecastDay
     */
    public void setForecastday(List<ForecastDay> forecastDay) {
        this.forecastday = forecastDay;
    }

}

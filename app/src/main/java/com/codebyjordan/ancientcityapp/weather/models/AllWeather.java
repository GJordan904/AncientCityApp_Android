
package com.codebyjordan.ancientcityapp.weather.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AllWeather {

    @SerializedName("forecast")
    @Expose
    private Forecast forecast;
    @SerializedName("hourly_forecast")
    @Expose
    private List<HourlyForecast> hourlyForecast = new ArrayList<HourlyForecast>();
    @SerializedName("tide")
    @Expose
    private Tide tide;


    public AllWeather() {
    }


    public AllWeather(Forecast forecast, List<HourlyForecast> hourlyForecast, Tide tide) {
        this.forecast = forecast;
        this.hourlyForecast = hourlyForecast;
        this.tide = tide;
    }

    public static AllWeather parseJson(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, AllWeather.class);
    }

    /**
     * 
     * @return
     *     The forecast
     */
    public Forecast getForecast() {
        return forecast;
    }

    /**
     * 
     * @param forecast
     *     The forecast
     */
    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    /**
     * 
     * @return
     *     The hourlyForecast
     */
    public List<HourlyForecast> getHourlyForecast() {
        return hourlyForecast;
    }

    /**
     * 
     * @param hourlyForecast
     *     The hourly_forecast
     */
    public void setHourlyForecast(List<HourlyForecast> hourlyForecast) {
        this.hourlyForecast = hourlyForecast;
    }

    /**
     * 
     * @return
     *     The tide
     */
    public Tide getTide() {
        return tide;
    }

    /**
     * 
     * @param tide
     *     The tide
     */
    public void setTide(Tide tide) {
        this.tide = tide;
    }

}

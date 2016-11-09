
package com.codebyjordan.ancientcityapp.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forecast {

    @SerializedName("txt_forecast")
    @Expose
    private TxtForecast txtForecast;
    @SerializedName("simpleforecast")
    @Expose
    private Simpleforecast simpleforecast;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Forecast() {
    }

    /**
     * 
     * @param simpleforecast
     * @param txtForecast
     */
    public Forecast(TxtForecast txtForecast, Simpleforecast simpleforecast) {
        this.txtForecast = txtForecast;
        this.simpleforecast = simpleforecast;
    }

    /**
     * 
     * @return
     *     The txtForecast
     */
    public TxtForecast getTxtForecast() {
        return txtForecast;
    }

    /**
     * 
     * @param txtForecast
     *     The txt_forecast
     */
    public void setTxtForecast(TxtForecast txtForecast) {
        this.txtForecast = txtForecast;
    }

    /**
     * 
     * @return
     *     The simpleforecast
     */
    public Simpleforecast getSimpleforecast() {
        return simpleforecast;
    }

    /**
     * 
     * @param simpleforecast
     *     The simpleforecast
     */
    public void setSimpleforecast(Simpleforecast simpleforecast) {
        this.simpleforecast = simpleforecast;
    }

}

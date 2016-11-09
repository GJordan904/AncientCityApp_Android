
package com.codebyjordan.ancientcityapp.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecastTime {

    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("min")
    @Expose
    private String min;
    @SerializedName("sec")
    @Expose
    private String sec;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ForecastTime() {
    }

    /**
     * 
     * @param min
     * @param sec
     * @param hour
     */
    public ForecastTime(String hour, String min, String sec) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }

    /**
     * 
     * @return
     *     The hour
     */
    public String getHour() {
        return hour;
    }

    /**
     * 
     * @param hour
     *     The hour
     */
    public void setHour(String hour) {
        this.hour = hour;
    }

    /**
     * 
     * @return
     *     The min
     */
    public String getMin() {
        return min;
    }

    /**
     * 
     * @param min
     *     The min
     */
    public void setMin(String min) {
        this.min = min;
    }

    /**
     * 
     * @return
     *     The sec
     */
    public String getSec() {
        return sec;
    }

    /**
     * 
     * @param sec
     *     The sec
     */
    public void setSec(String sec) {
        this.sec = sec;
    }

}

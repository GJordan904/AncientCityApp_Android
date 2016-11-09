
package com.codebyjordan.ancientcityapp.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TideDate {

    @SerializedName("pretty")
    @Expose
    private String pretty;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("mon")
    @Expose
    private String mon;
    @SerializedName("mday")
    @Expose
    private String mday;
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("min")
    @Expose
    private String min;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TideDate() {
    }

    /**
     * 
     * @param min
     * @param mday
     * @param mon
     * @param year
     * @param hour
     * @param pretty
     */
    public TideDate(String pretty, String year, String mon, String mday, String hour, String min) {
        this.pretty = pretty;
        this.year = year;
        this.mon = mon;
        this.mday = mday;
        this.hour = hour;
        this.min = min;
    }

    /**
     * 
     * @return
     *     The pretty
     */
    public String getPretty() {
        return pretty;
    }

    /**
     * 
     * @param pretty
     *     The pretty
     */
    public void setPretty(String pretty) {
        this.pretty = pretty;
    }

    /**
     * 
     * @return
     *     The year
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * @param year
     *     The year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * @return
     *     The mon
     */
    public String getMon() {
        return mon;
    }

    /**
     * 
     * @param mon
     *     The mon
     */
    public void setMon(String mon) {
        this.mon = mon;
    }

    /**
     * 
     * @return
     *     The mday
     */
    public String getMday() {
        return mday;
    }

    /**
     * 
     * @param mday
     *     The mday
     */
    public void setMday(String mday) {
        this.mday = mday;
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

}

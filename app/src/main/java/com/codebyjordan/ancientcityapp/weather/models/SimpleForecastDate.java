
package com.codebyjordan.ancientcityapp.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SimpleForecastDate {

    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("month")
    @Expose
    private Integer month;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("hour")
    @Expose
    private int hour;
    @SerializedName("monthname")
    @Expose
    private String monthname;
    @SerializedName("weekday")
    @Expose
    private String weekday;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SimpleForecastDate() {
    }

    /**
     * 
     * @param weekday
     * @param month
     * @param year
     * @param day
     */
    public SimpleForecastDate(Integer day, Integer month, Integer year, String monthname, String weekday, int hour) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.monthname = monthname;
        this.weekday = weekday;
        this.hour = hour;
    }

    /**
     * 
     * @return
     *     The day
     */
    public Integer getDay() {
        return day;
    }

    /**
     * 
     * @param day
     *     The day
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * 
     * @return
     *     The month
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * 
     * @param month
     *     The month
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * 
     * @return
     *     The year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * 
     * @param year
     *     The year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMonthname() {
        return monthname;
    }

    public void setMonthname(String monthname) {
        this.monthname = monthname;
    }

    /**
     * 
     * @return
     *     The weekday
     */
    public String getWeekday() {
        return weekday;
    }

    /**
     * 
     * @param weekday
     *     The weekday
     */
    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}

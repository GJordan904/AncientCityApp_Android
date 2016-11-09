
package com.codebyjordan.ancientcityapp.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TideSummary {

    @SerializedName("date")
    @Expose
    private TideDate date;
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TideSummary() {
    }

    /**
     * 
     * @param data
     * @param date
     */
    public TideSummary(TideDate date, Data data) {
        this.date = date;
        this.data = data;
    }

    /**
     * 
     * @return
     *     The date
     */
    public TideDate getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(TideDate date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The data
     */
    public Data getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data data) {
        this.data = data;
    }

}

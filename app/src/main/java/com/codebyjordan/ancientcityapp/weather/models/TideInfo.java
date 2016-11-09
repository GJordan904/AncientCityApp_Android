
package com.codebyjordan.ancientcityapp.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TideInfo {

    @SerializedName("tideSite")
    @Expose
    private String tideSite;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TideInfo() {
    }

    /**
     * 
     * @param lon
     * @param tideSite
     * @param lat
     */
    public TideInfo(String tideSite, String lat, String lon) {
        this.tideSite = tideSite;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * 
     * @return
     *     The tideSite
     */
    public String getTideSite() {
        return tideSite;
    }

    /**
     * 
     * @param tideSite
     *     The tideSite
     */
    public void setTideSite(String tideSite) {
        this.tideSite = tideSite;
    }

    /**
     * 
     * @return
     *     The lat
     */
    public String getLat() {
        return lat;
    }

    /**
     * 
     * @param lat
     *     The lat
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * 
     * @return
     *     The lon
     */
    public String getLon() {
        return lon;
    }

    /**
     * 
     * @param lon
     *     The lon
     */
    public void setLon(String lon) {
        this.lon = lon;
    }

}

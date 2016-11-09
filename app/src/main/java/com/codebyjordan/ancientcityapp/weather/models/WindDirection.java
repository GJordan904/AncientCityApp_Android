
package com.codebyjordan.ancientcityapp.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WindDirection {

    @SerializedName("dir")
    @Expose
    private String dir;
    @SerializedName("degrees")
    @Expose
    private String degrees;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WindDirection() {
    }

    /**
     * 
     * @param dir
     * @param degrees
     */
    public WindDirection(String dir, String degrees) {
        this.dir = dir;
        this.degrees = degrees;
    }

    /**
     * 
     * @return
     *     The dir
     */
    public String getDir() {
        return dir;
    }

    /**
     * 
     * @param dir
     *     The dir
     */
    public void setDir(String dir) {
        this.dir = dir;
    }

    /**
     * 
     * @return
     *     The degrees
     */
    public String getDegrees() {
        return degrees;
    }

    /**
     * 
     * @param degrees
     *     The degrees
     */
    public void setDegrees(String degrees) {
        this.degrees = degrees;
    }

}

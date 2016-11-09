
package com.codebyjordan.ancientcityapp.weather.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AverageWind {

    @SerializedName("mph")
    @Expose
    private Integer mph;
    @SerializedName("kph")
    @Expose
    private Integer kph;
    @SerializedName("dir")
    @Expose
    private String dir;
    @SerializedName("degrees")
    @Expose
    private Integer degrees;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AverageWind() {
    }

    /**
     * 
     * @param mph
     * @param dir
     * @param degrees
     * @param kph
     */
    public AverageWind(Integer mph, Integer kph, String dir, Integer degrees) {
        this.mph = mph;
        this.kph = kph;
        this.dir = dir;
        this.degrees = degrees;
    }

    /**
     * 
     * @return
     *     The mph
     */
    public Integer getMph() {
        return mph;
    }

    /**
     * 
     * @param mph
     *     The mph
     */
    public void setMph(Integer mph) {
        this.mph = mph;
    }

    /**
     * 
     * @return
     *     The kph
     */
    public Integer getKph() {
        return kph;
    }

    /**
     * 
     * @param kph
     *     The kph
     */
    public void setKph(Integer kph) {
        this.kph = kph;
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
    public Integer getDegrees() {
        return degrees;
    }

    /**
     * 
     * @param degrees
     *     The degrees
     */
    public void setDegrees(Integer degrees) {
        this.degrees = degrees;
    }

}

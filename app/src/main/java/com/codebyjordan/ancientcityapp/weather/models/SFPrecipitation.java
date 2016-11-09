
package com.codebyjordan.ancientcityapp.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SFPrecipitation {

    @SerializedName("in")
    @Expose
    private Double in;
    @SerializedName("mm")
    @Expose
    private Integer mm;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SFPrecipitation() {
    }

    /**
     * 
     * @param mm
     * @param in
     */
    public SFPrecipitation(Double in, Integer mm) {
        this.in = in;
        this.mm = mm;
    }

    /**
     * 
     * @return
     *     The in
     */
    public Double getIn() {
        return in;
    }

    /**
     * 
     * @param in
     *     The in
     */
    public void setIn(Double in) {
        this.in = in;
    }

    /**
     * 
     * @return
     *     The mm
     */
    public Integer getMm() {
        return mm;
    }

    /**
     * 
     * @param mm
     *     The mm
     */
    public void setMm(Integer mm) {
        this.mm = mm;
    }

}

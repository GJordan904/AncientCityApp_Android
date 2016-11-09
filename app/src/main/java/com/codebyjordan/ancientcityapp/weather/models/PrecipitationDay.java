
package com.codebyjordan.ancientcityapp.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrecipitationDay {

    @SerializedName("in")
    @Expose
    private Object in;
    @SerializedName("mm")
    @Expose
    private Object mm;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PrecipitationDay() {
    }

    /**
     * 
     * @param mm
     * @param in
     */
    public PrecipitationDay(Object in, Object mm) {
        this.in = in;
        this.mm = mm;
    }

    /**
     * 
     * @return
     *     The in
     */
    public Object getIn() {
        return in;
    }

    /**
     * 
     * @param in
     *     The in
     */
    public void setIn(Object in) {
        this.in = in;
    }

    /**
     * 
     * @return
     *     The mm
     */
    public Object getMm() {
        return mm;
    }

    /**
     * 
     * @param mm
     *     The mm
     */
    public void setMm(Object mm) {
        this.mm = mm;
    }

}

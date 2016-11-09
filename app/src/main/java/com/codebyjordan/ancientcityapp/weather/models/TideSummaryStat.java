
package com.codebyjordan.ancientcityapp.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TideSummaryStat {

    @SerializedName("maxheight")
    @Expose
    private Double maxheight;
    @SerializedName("minheight")
    @Expose
    private Double minheight;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TideSummaryStat() {
    }

    /**
     * 
     * @param maxheight
     * @param minheight
     */
    public TideSummaryStat(Double maxheight, Double minheight) {
        this.maxheight = maxheight;
        this.minheight = minheight;
    }

    /**
     * 
     * @return
     *     The maxheight
     */
    public Double getMaxheight() {
        return maxheight;
    }

    /**
     * 
     * @param maxheight
     *     The maxheight
     */
    public void setMaxheight(Double maxheight) {
        this.maxheight = maxheight;
    }

    /**
     * 
     * @return
     *     The minheight
     */
    public Double getMinheight() {
        return minheight;
    }

    /**
     * 
     * @param minheight
     *     The minheight
     */
    public void setMinheight(Double minheight) {
        this.minheight = minheight;
    }

}

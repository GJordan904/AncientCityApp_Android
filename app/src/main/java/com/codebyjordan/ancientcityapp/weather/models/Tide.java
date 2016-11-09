
package com.codebyjordan.ancientcityapp.weather.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tide {

    @SerializedName("tideInfo")
    @Expose
    private List<TideInfo> tideInfo = new ArrayList<TideInfo>();
    @SerializedName("tideSummary")
    @Expose
    private List<TideSummary> tideSummary = new ArrayList<TideSummary>();
    @SerializedName("tideSummaryStats")
    @Expose
    private List<TideSummaryStat> tideSummaryStats = new ArrayList<TideSummaryStat>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Tide() {
    }

    /**
     * 
     * @param tideSummary
     * @param tideSummaryStats
     * @param tideInfo
     */
    public Tide(List<TideInfo> tideInfo, List<TideSummary> tideSummary, List<TideSummaryStat> tideSummaryStats) {
        this.tideInfo = tideInfo;
        this.tideSummary = tideSummary;
        this.tideSummaryStats = tideSummaryStats;
    }

    /**
     * 
     * @return
     *     The tideInfo
     */
    public List<TideInfo> getTideInfo() {
        return tideInfo;
    }

    /**
     * 
     * @param tideInfo
     *     The tideInfo
     */
    public void setTideInfo(List<TideInfo> tideInfo) {
        this.tideInfo = tideInfo;
    }

    /**
     * 
     * @return
     *     The tideSummary
     */
    public List<TideSummary> getTideSummary() {
        return tideSummary;
    }

    /**
     * 
     * @param tideSummary
     *     The tideSummary
     */
    public void setTideSummary(List<TideSummary> tideSummary) {
        this.tideSummary = tideSummary;
    }

    /**
     * 
     * @return
     *     The tideSummaryStats
     */
    public List<TideSummaryStat> getTideSummaryStats() {
        return tideSummaryStats;
    }

    /**
     * 
     * @param tideSummaryStats
     *     The tideSummaryStats
     */
    public void setTideSummaryStats(List<TideSummaryStat> tideSummaryStats) {
        this.tideSummaryStats = tideSummaryStats;
    }

}

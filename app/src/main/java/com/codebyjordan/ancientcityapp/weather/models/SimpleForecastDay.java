
package com.codebyjordan.ancientcityapp.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SimpleForecastDay {

    @SerializedName("date")
    @Expose
    private SimpleForecastDate date;
    @SerializedName("period")
    @Expose
    private Integer period;
    @SerializedName("high")
    @Expose
    private High high;
    @SerializedName("low")
    @Expose
    private Low low;
    @SerializedName("conditions")
    @Expose
    private String conditions;
    @SerializedName("qpf_allday")
    @Expose
    private SFPrecipitation precipitation;
    @SerializedName("qpf_day")
    @Expose
    private PrecipitationDay precipitationDay;
    @SerializedName("qpf_night")
    @Expose
    private PrecipitationNight precipitationNight;
    @SerializedName("maxwind")
    @Expose
    private MaxWind maxwind;
    @SerializedName("avewind")
    @Expose
    private AverageWind avewind;
    @SerializedName("avehumidity")
    @Expose
    private Integer avehumidity;
    @SerializedName("maxhumidity")
    @Expose
    private Integer maxhumidity;
    @SerializedName("minhumidity")
    @Expose
    private Integer minhumidity;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SimpleForecastDay() {
    }

    /**
     * 
     * @param conditions
     * @param maxWind
     * @param averageWind
     * @param precipitationDay
     * @param SFPrecipitation
     * @param avehumidity
     * @param precipitationNight
     * @param minhumidity
     * @param high
     * @param low
     * @param period
     * @param date
     * @param maxhumidity
     */
    public SimpleForecastDay(SimpleForecastDate date, Integer period, High high, Low low, String conditions, SFPrecipitation SFPrecipitation, PrecipitationDay precipitationDay, PrecipitationNight precipitationNight, MaxWind maxWind, AverageWind averageWind, Integer avehumidity, Integer maxhumidity, Integer minhumidity) {
        this.date = date;
        this.period = period;
        this.high = high;
        this.low = low;
        this.conditions = conditions;
        this.precipitation = SFPrecipitation;
        this.precipitationDay = precipitationDay;
        this.precipitationNight = precipitationNight;
        this.maxwind = maxWind;
        this.avewind = averageWind;
        this.avehumidity = avehumidity;
        this.maxhumidity = maxhumidity;
        this.minhumidity = minhumidity;
    }

    /**
     * 
     * @return
     *     The date
     */
    public SimpleForecastDate getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(SimpleForecastDate date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The period
     */
    public Integer getPeriod() {
        return period;
    }

    /**
     * 
     * @param period
     *     The period
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }

    /**
     * 
     * @return
     *     The high
     */
    public High getHigh() {
        return high;
    }

    /**
     * 
     * @param high
     *     The high
     */
    public void setHigh(High high) {
        this.high = high;
    }

    /**
     * 
     * @return
     *     The low
     */
    public Low getLow() {
        return low;
    }

    /**
     * 
     * @param low
     *     The low
     */
    public void setLow(Low low) {
        this.low = low;
    }

    /**
     * 
     * @return
     *     The conditions
     */
    public String getConditions() {
        return conditions;
    }

    /**
     * 
     * @param conditions
     *     The conditions
     */
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    /**
     * 
     * @return
     *     The precipitation
     */
    public SFPrecipitation getPrecipitation() {
        return precipitation;
    }

    /**
     * 
     * @param SFPrecipitation
     *     The qpf_allday
     */
    public void setPrecipitation(SFPrecipitation SFPrecipitation) {
        this.precipitation = SFPrecipitation;
    }

    /**
     * 
     * @return
     *     The precipitationDay
     */
    public PrecipitationDay getPrecipitationDay() {
        return precipitationDay;
    }

    /**
     * 
     * @param precipitationDay
     *     The qpf_day
     */
    public void setPrecipitationDay(PrecipitationDay precipitationDay) {
        this.precipitationDay = precipitationDay;
    }

    /**
     * 
     * @return
     *     The precipitationNight
     */
    public PrecipitationNight getPrecipitationNight() {
        return precipitationNight;
    }

    /**
     * 
     * @param precipitationNight
     *     The qpf_night
     */
    public void setPrecipitationNight(PrecipitationNight precipitationNight) {
        this.precipitationNight = precipitationNight;
    }

    /**
     * 
     * @return
     *     The maxwind
     */
    public MaxWind getMaxwind() {
        return maxwind;
    }

    /**
     * 
     * @param maxWind
     *     The maxWind
     */
    public void setMaxwind(MaxWind maxWind) {
        this.maxwind = maxWind;
    }

    /**
     * 
     * @return
     *     The avewind
     */
    public AverageWind getAvewind() {
        return avewind;
    }

    /**
     * 
     * @param averageWind
     *     The averageWind
     */
    public void setAvewind(AverageWind averageWind) {
        this.avewind = averageWind;
    }

    /**
     * 
     * @return
     *     The avehumidity
     */
    public Integer getAvehumidity() {
        return avehumidity;
    }

    /**
     * 
     * @param avehumidity
     *     The avehumidity
     */
    public void setAvehumidity(Integer avehumidity) {
        this.avehumidity = avehumidity;
    }

    /**
     * 
     * @return
     *     The maxhumidity
     */
    public Integer getMaxhumidity() {
        return maxhumidity;
    }

    /**
     * 
     * @param maxhumidity
     *     The maxhumidity
     */
    public void setMaxhumidity(Integer maxhumidity) {
        this.maxhumidity = maxhumidity;
    }

    /**
     * 
     * @return
     *     The minhumidity
     */
    public Integer getMinhumidity() {
        return minhumidity;
    }

    /**
     * 
     * @param minhumidity
     *     The minhumidity
     */
    public void setMinhumidity(Integer minhumidity) {
        this.minhumidity = minhumidity;
    }

}


package com.codebyjordan.ancientcityapp.weather.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HourlyForecast {

    @SerializedName("ForecastTime")
    @Expose
    private ForecastTime fCTTIME;
    @SerializedName("temp")
    @Expose
    private Temp temp;
    @SerializedName("dewpoint")
    @Expose
    private Dewpoint dewpoint;
    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("sky")
    @Expose
    private String sky;
    @SerializedName("wspd")
    @Expose
    private WindSpeed wspd;
    @SerializedName("wdir")
    @Expose
    private WindDirection wdir;
    @SerializedName("wx")
    @Expose
    private String wx;
    @SerializedName("uvi")
    @Expose
    private String uvi;
    @SerializedName("humidity")
    @Expose
    private String humidity;
    @SerializedName("feelslike")
    @Expose
    private FeelsLike feelslike;
    @SerializedName("qpf")
    @Expose
    private Precipitation precipitation;
    @SerializedName("pop")
    @Expose
    private String pop;


    /**
     * No args constructor for use in serialization
     * 
     */
    public HourlyForecast() {
    }

    /**
     * 
     * @param uvi
     * @param icon
     * @param condition
     * @param windSpeed
     * @param pop
     * @param windDirection
     * @param feelsLike
     * @param wx
     * @param precipitation
     * @param sky
     * @param humidity
     * @param dewpoint
     * @param temp
     * @param fCTTIME
     */
    public HourlyForecast(ForecastTime fCTTIME, Temp temp, Dewpoint dewpoint, String condition, String icon, String sky, WindSpeed windSpeed, WindDirection windDirection, String wx, String uvi, String humidity, FeelsLike feelsLike, Precipitation precipitation, String pop) {
        this.fCTTIME = fCTTIME;
        this.temp = temp;
        this.dewpoint = dewpoint;
        this.condition = condition;
        this.icon = icon;
        this.sky = sky;
        this.wspd = windSpeed;
        this.wdir = windDirection;
        this.wx = wx;
        this.uvi = uvi;
        this.humidity = humidity;
        this.feelslike = feelsLike;
        this.precipitation = precipitation;
        this.pop = pop;
    }

    /**
     * 
     * @return
     *     The fCTTIME
     */
    public ForecastTime getFCTTIME() {
        return fCTTIME;
    }

    /**
     * 
     * @param fCTTIME
     *     The ForecastTime
     */
    public void setFCTTIME(ForecastTime fCTTIME) {
        this.fCTTIME = fCTTIME;
    }

    /**
     * 
     * @return
     *     The temp
     */
    public Temp getTemp() {
        return temp;
    }

    /**
     * 
     * @param temp
     *     The temp
     */
    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    /**
     * 
     * @return
     *     The dewpoint
     */
    public Dewpoint getDewpoint() {
        return dewpoint;
    }

    /**
     * 
     * @param dewpoint
     *     The dewpoint
     */
    public void setDewpoint(Dewpoint dewpoint) {
        this.dewpoint = dewpoint;
    }

    /**
     * 
     * @return
     *     The condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * 
     * @param condition
     *     The condition
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * 
     * @return
     *     The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 
     * @param icon
     *     The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 
     * @return
     *     The sky
     */
    public String getSky() {
        return sky;
    }

    /**
     * 
     * @param sky
     *     The sky
     */
    public void setSky(String sky) {
        this.sky = sky;
    }

    /**
     * 
     * @return
     *     The wspd
     */
    public WindSpeed getWspd() {
        return wspd;
    }

    /**
     * 
     * @param windSpeed
     *     The windSpeed
     */
    public void setWspd(WindSpeed windSpeed) {
        this.wspd = windSpeed;
    }

    /**
     * 
     * @return
     *     The wdir
     */
    public WindDirection getWdir() {
        return wdir;
    }

    /**
     * 
     * @param windDirection
     *     The windDirection
     */
    public void setWdir(WindDirection windDirection) {
        this.wdir = windDirection;
    }

    /**
     * 
     * @return
     *     The wx
     */
    public String getWx() {
        return wx;
    }

    /**
     * 
     * @param wx
     *     The wx
     */
    public void setWx(String wx) {
        this.wx = wx;
    }

    /**
     * 
     * @return
     *     The uvi
     */
    public String getUvi() {
        return uvi;
    }

    /**
     * 
     * @param uvi
     *     The uvi
     */
    public void setUvi(String uvi) {
        this.uvi = uvi;
    }

    /**
     * 
     * @return
     *     The humidity
     */
    public String getHumidity() {
        return humidity;
    }

    /**
     * 
     * @param humidity
     *     The humidity
     */
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    /**
     * 
     * @return
     *     The feelslike
     */
    public FeelsLike getFeelslike() {
        return feelslike;
    }

    /**
     * 
     * @param feelsLike
     *     The feelsLike
     */
    public void setFeelslike(FeelsLike feelsLike) {
        this.feelslike = feelsLike;
    }

    /**
     * 
     * @return
     *     The precipitation
     */
    public Precipitation getPrecipitation() {
        return precipitation;
    }

    /**
     * 
     * @param precipitation
     *     The precipitation
     */
    public void setPrecipitation(Precipitation precipitation) {
        this.precipitation = precipitation;
    }

    /**
     * 
     * @return
     *     The pop
     */
    public String getPop() {
        return pop;
    }

    /**
     * 
     * @param pop
     *     The pop
     */
    public void setPop(String pop) {
        this.pop = pop;
    }

}

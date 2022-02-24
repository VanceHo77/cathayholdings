package com.cathayholdings.homework.dto;

import java.util.LinkedHashMap;

public class BpiCurrentpriceDTO {

    private Time time;

    private String disclaimer;

    private String chartName;

    private LinkedHashMap<String, CurrencyRate> bpi;

    public BpiCurrentpriceDTO() {
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public LinkedHashMap<String, CurrencyRate> getBpi() {
        return bpi;
    }

    public void setBpi(LinkedHashMap<String, CurrencyRate> bpi) {
        this.bpi = bpi;
    }
}

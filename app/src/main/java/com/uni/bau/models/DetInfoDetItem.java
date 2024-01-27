package com.uni.bau.models;

public class DetInfoDetItem {
    private String detType;
    private String endDate;
    private String startDate;
    private String period;

    public DetInfoDetItem(String detType, String endDate, String startDate, String period) {
        this.detType = detType;
        this.endDate = endDate;
        this.startDate = startDate;
        this.period = period;
    }

    public String getDetType() {
        return detType;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getPeriod() {
        return period;
    }
}

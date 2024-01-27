package com.uni.bau.models;


public class DetInfoMainItem {
    private String lokDesc;
    private String usedBalance;
    private String remainBalance;
    private String yearlyBalance;
    private String year;

    public DetInfoMainItem(String lokDesc, String usedBalance, String remainBalance, String yearlyBalance, String year) {
        this.lokDesc = lokDesc;
        this.usedBalance = usedBalance;
        this.remainBalance = remainBalance;
        this.yearlyBalance = yearlyBalance;
        this.year = year;
    }

    public String getLokDesc() {
        return lokDesc;
    }

    public String getUsedBalance() {
        return usedBalance;
    }

    public String getRemainBalance() {
        return remainBalance;
    }

    public String getYearlyBalance() {
        return yearlyBalance;
    }

    public String getYear() {
        return year;
    }
}

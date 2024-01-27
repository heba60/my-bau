package com.uni.bau.models;

public class HealthModel {
    private String mptypeDesc;
    private String subsNetAmt;
    private String patDesc;
    private String clmDate;

    public HealthModel(String mptypeDesc, String subsNetAmt, String patDesc, String clmDate) {
        this.mptypeDesc = mptypeDesc;
        this.subsNetAmt = subsNetAmt;
        this.patDesc = patDesc;
        this.clmDate = clmDate;
    }

    public String getMptypeDesc() {
        return mptypeDesc;
    }

    public String getSubsNetAmt() {
        return subsNetAmt;
    }

    public String getPatDesc() {
        return patDesc;
    }

    public String getClmDate() {
        return clmDate;
    }
}


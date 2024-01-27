package com.uni.bau.models;

public class PlanInfo {

    private String allHours;
    private String achivedHours;
    private boolean isItDone;


    public String getAllHours() {
        return allHours;
    }

    public void setAllHours(String allHours) {
        this.allHours = allHours;
    }

    public String getAchivedHours() {
        return achivedHours;
    }

    public void setAchivedHours(String achivedHours) {
        this.achivedHours = achivedHours;
    }

    public boolean isItDone() {
        return isItDone;
    }

    public void setItDone(boolean itDone) {
        isItDone = itDone;
    }
}

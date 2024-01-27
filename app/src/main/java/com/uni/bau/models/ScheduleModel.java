package com.uni.bau.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleModel {
    @SerializedName("lecNum")
    @Expose
    private String lecNum;
    @SerializedName("lecName")
    @Expose
    private String lecName;
    @SerializedName("classID")
    @Expose
    private String classID;
    @SerializedName("hours")
    @Expose
    private String hours;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("days")
    @Expose
    private String days;
    @SerializedName("hall")
    @Expose
    private String hall;
    @SerializedName("instructor")
    @Expose
    private String instructor;
    @SerializedName("registerDate")
    @Expose
    private String registerDate;


    public String getLecNum() {
        return lecNum;
    }

    public void setLecNum(String lecNum) {
        this.lecNum = lecNum;
    }

    public String getLecName() {
        return lecName;
    }

    public void setLecName(String lecName) {
        this.lecName = lecName;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
}


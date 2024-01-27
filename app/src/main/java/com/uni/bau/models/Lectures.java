package com.uni.bau.models;

public class Lectures {
    private String Number;
    private String Name;
    private String SectionNumber;
    private String LectureTime;
    private String Days;


    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSectionNumber() {
        return SectionNumber;
    }

    public void setSectionNumber(String sectionNumber) {
        SectionNumber = sectionNumber;
    }

    public String getLectureTime() {
        return LectureTime;
    }

    public void setLectureTime(String lectureTime) {
        LectureTime = lectureTime;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }
}

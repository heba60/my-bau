package com.uni.bau.models;

import java.util.List;

public class SchedulePost {

    private List<Lectures> Lectures;
    private int Year;
    private int Semester;

    public List<Lectures> getLectures() {
        return Lectures;
    }

    public void setLectures(List<Lectures> lectures) {
        Lectures = lectures;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getSemester() {
        return Semester;
    }

    public void setSemester(int semester) {
        Semester = semester;
    }
}

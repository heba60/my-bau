package com.uni.bau.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetStudentLecutresModel {

    public class Datum {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Number")
        @Expose
        private String number;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("SectionNumber")
        @Expose
        private String sectionNumber;
        @SerializedName("LectureTime")
        @Expose
        private String lectureTime;
        @SerializedName("Days")
        @Expose
        private String days;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSectionNumber() {
            return sectionNumber;
        }

        public void setSectionNumber(String sectionNumber) {
            this.sectionNumber = sectionNumber;
        }

        public String getLectureTime() {
            return lectureTime;
        }

        public void setLectureTime(String lectureTime) {
            this.lectureTime = lectureTime;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

    }
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
}

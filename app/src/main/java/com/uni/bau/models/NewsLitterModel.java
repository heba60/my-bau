package com.uni.bau.models;

import com.google.gson.annotations.SerializedName;

public class NewsLitterModel {


        @SerializedName("name")
        private String name;

        @SerializedName("hours")
        private int hours;

        @SerializedName("status")
        private int status;

        @SerializedName("rooms")
        private String rooms;

        @SerializedName("times")
        private String times;

        @SerializedName("days")
        private String days;

        @SerializedName("lecturers")
        private String lecturers;

        @SerializedName("no")
        private String no;

        @SerializedName("sectionNo")
        private int sectionNo;

        @SerializedName("remarks")
        private String remarks;

        // Getters and setters

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRooms() {
            return rooms;
        }

        public void setRooms(String rooms) {
            this.rooms = rooms;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getLecturers() {
            return lecturers;
        }

        public void setLecturers(String lecturers) {
            this.lecturers = lecturers;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public int getSectionNo() {
            return sectionNo;
        }

        public void setSectionNo(int sectionNo) {
            this.sectionNo = sectionNo;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

}

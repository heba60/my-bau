package com.uni.bau.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarksApiModel {


        @SerializedName("hours")
        @Expose
        private String hours;
        @SerializedName("year")
        @Expose
        private String year;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("marks")
        @Expose
        private List<Mark> marks;
        @SerializedName("semester")
        @Expose
        private String semester;
        @SerializedName("average")
        @Expose
        private String average;
        @SerializedName("finalAverage")
        @Expose
        private String finalAverage;
        @SerializedName("accumulativeHours")
        @Expose
        private String accumulativeHours;
        @SerializedName("accumulativePassHours")
        @Expose
        private String accumulativePassHours;
        @SerializedName("remarks")
        @Expose
        private String remarks;

        public String getHours() {
            return hours;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Mark> getMarks() {
            return marks;
        }

        public void setMarks(List<Mark> marks) {
            this.marks = marks;
        }

        public String getSemester() {
            return semester;
        }

        public void setSemester(String semester) {
            this.semester = semester;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public String getFinalAverage() {
            return finalAverage;
        }

        public void setFinalAverage(String finalAverage) {
            this.finalAverage = finalAverage;
        }

        public String getAccumulativeHours() {
            return accumulativeHours;
        }

        public void setAccumulativeHours(String accumulativeHours) {
            this.accumulativeHours = accumulativeHours;
        }

        public String getAccumulativePassHours() {
            return accumulativePassHours;
        }

        public void setAccumulativePassHours(String accumulativePassHours) {
            this.accumulativePassHours = accumulativePassHours;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }


    public class Mark {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("hours")
        @Expose
        private String hours;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("result")
        @Expose
        private String result;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHours() {
            return hours;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

    }
}

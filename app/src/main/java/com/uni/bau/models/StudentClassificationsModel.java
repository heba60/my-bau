package com.uni.bau.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentClassificationsModel {



        private int id;
        private String englishName;
        private String arabicName;
        private int maxHours;
        private int currentHours;
        private List<Mark> marks;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }

        public String getArabicName() {
            return arabicName;
        }

        public void setArabicName(String arabicName) {
            this.arabicName = arabicName;
        }

        public int getMaxHours() {
            return maxHours;
        }

        public void setMaxHours(int maxHours) {
            this.maxHours = maxHours;
        }

        public int getCurrentHours() {
            return currentHours;
        }

        public void setCurrentHours(int currentHours) {
            this.currentHours = currentHours;
        }

        public List<Mark> getMarks() {
            return marks;
        }

        public void setMarks(List<Mark> marks) {
            this.marks = marks;
        }


    public class Mark {
        private String name;
        private String hours;
        private String id;
        private String status;
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

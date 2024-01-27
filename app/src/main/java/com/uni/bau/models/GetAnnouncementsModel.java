package com.uni.bau.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAnnouncementsModel {



        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("text")
        @Expose
        private String text;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

}

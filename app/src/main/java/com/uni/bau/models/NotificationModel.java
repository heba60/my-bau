package com.uni.bau.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationModel {


    public class Datum {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Body")
        @Expose
        private String body;
        @SerializedName("NotificationDate")
        @Expose
        private String notificationDate;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getNotificationDate() {
            return notificationDate;
        }

        public void setNotificationDate(String notificationDate) {
            this.notificationDate = notificationDate;
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

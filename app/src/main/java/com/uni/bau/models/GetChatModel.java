package com.uni.bau.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetChatModel implements Serializable {
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("Msg")
        @Expose
        private String msg;
        @SerializedName("Files")
        @Expose
        private List<Object> files = null;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("SenderId")
        @Expose
        private String senderId;
        @SerializedName("SenderName")
        @Expose
        private String senderName;
        @SerializedName("SenderType")
        @Expose
        private String senderType;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<Object> getFiles() {
            return files;
        }

        public void setFiles(List<Object> files) {
            this.files = files;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public String getSenderType() {
            return senderType;
        }

        public void setSenderType(String senderType) {
            this.senderType = senderType;
        }

    }
}

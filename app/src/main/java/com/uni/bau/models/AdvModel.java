package com.uni.bau.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdvModel {

    public class Datum {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Title")
        @Expose
        private String Title;
        @SerializedName("Description")
        @Expose
        private String Description;
        @SerializedName("Image")
        @Expose
        private String Image;
        @SerializedName("Url")
        @Expose
        private String Url;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getImage() {
            return Image;
        }

        public void setImage(String image) {
            Image = image;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String url) {
            Url = url;
        }
    }

        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

}

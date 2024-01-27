package com.uni.bau.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobsModel {

    public class Datum {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("Date")
        @Expose
        private String date;
        @SerializedName("Image")
        @Expose
        private String image;
        @SerializedName("LinkType")
        @Expose
        private Integer linkType;
        @SerializedName("Link")
        @Expose
        private String link;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getLinkType() {
            return linkType;
        }

        public void setLinkType(Integer linkType) {
            this.linkType = linkType;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
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

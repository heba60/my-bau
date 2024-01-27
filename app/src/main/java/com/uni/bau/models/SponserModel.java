package com.uni.bau.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SponserModel {

    public class Datum {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Logo")
        @Expose
        private String logo;
        @SerializedName("Url")
        @Expose
        private String url;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

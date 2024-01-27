package com.uni.bau.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckNumberModel {

    @SerializedName("data")
    @Expose
    private Boolean data;

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }
}

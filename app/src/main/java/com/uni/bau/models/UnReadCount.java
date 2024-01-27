package com.uni.bau.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnReadCount {
    @SerializedName("data")
    @Expose
    private Integer data;

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }
}


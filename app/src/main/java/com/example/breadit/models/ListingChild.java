package com.example.breadit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListingChild {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private ChildData data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ChildData getData() {
        return data;
    }

    public void setData(ChildData data) {
        this.data = data;
    }

}

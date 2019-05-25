package com.example.breadit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedditListing {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("listingData")
    @Expose
    private ListingData data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ListingData getListingData() {
        return data;
    }

    public void setListingData(ListingData listingData) {
        this.data = listingData;
    }

}

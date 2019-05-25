package com.example.breadit.network;

import com.example.breadit.models.RedditListing;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RedditClient {
    @GET
    Call<RedditListing> get();
}

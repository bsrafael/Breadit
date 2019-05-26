package com.example.breadit.network;

import com.example.breadit.models.RedditListing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RedditClient {
    @GET("r/{sub}.json")
    Call<RedditListing> get(@Path(value = "sub", encoded = false) String sub);

    @GET("r/{sub}.json")
    Call<RedditListing> getAfter(@Path(value = "sub", encoded = false) String sub, @Query("count") int count, @Query("after") String after);
}

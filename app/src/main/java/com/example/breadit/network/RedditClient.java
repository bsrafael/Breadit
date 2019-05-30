package com.example.breadit.network;

import com.example.breadit.models.RedditListing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RedditClient {
    @GET("r/{sub}.json")
    Call<RedditListing> get(@Path(value = "sub", encoded = false) String sub);

    @GET("r/{sub}.json")
    Call<RedditListing> getAfter(@Path(value = "sub", encoded = false) String sub, @Query("count") int count, @Query("after") String after);

    @GET("r/{sub}/comments/{id}.json")
    Call<List<RedditListing>> getPost(@Path(value = "sub", encoded = false) String sub, @Path(value = "id", encoded = false) String id);
}

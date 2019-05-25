package com.example.breadit.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ClientFactory {
    public static final String BASE_URL = "old.reddit.com/";

    public static final RedditClient GetSubRedditClient(String sub) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + "r/" + sub)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RedditClient.class);
    }
}

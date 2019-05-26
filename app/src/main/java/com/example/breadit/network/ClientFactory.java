package com.example.breadit.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ClientFactory {
    public static final String BASE_URL = "https://old.reddit.com/";

    public static final RedditClient GetRedditClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RedditClient.class);
    }
}

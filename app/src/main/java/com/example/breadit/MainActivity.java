package com.example.breadit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.breadit.network.ClientFactory;
import com.example.breadit.network.RedditClient;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_posts;
    private RedditClient breaditClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        breaditClient = ClientFactory.GetRedditClient();

        rv_posts = findViewById(R.id.rv_posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_posts.setLayoutManager(layoutManager);

        // TODO a loading screen would be cool

        PostAdapter adapter = new PostAdapter(breaditClient, rv_posts);
        rv_posts.setAdapter(adapter);
    }

}

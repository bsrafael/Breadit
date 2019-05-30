package com.example.breadit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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




    //  menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_feed:
                showPostsFromFeed();
                return true;
            case R.id.menu_salvos:
                showPostsFromDatabase();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //    TODO: implement logic to go back to feed (default) view.
    private void showPostsFromFeed(){
        try {
            this.getActionBar().setTitle(R.string.app_feed);
        } catch (Exception e) {
            getSupportActionBar().setTitle(R.string.app_feed);
        }

        PostAdapter adapter = new PostAdapter(breaditClient, rv_posts);
        rv_posts.setAdapter(adapter);

    }

    //    TODO: implement logic to go to database view.
    private void showPostsFromDatabase(){
        try {
            this.getActionBar().setTitle(R.string.app_database);
        } catch (Exception e) {
            getSupportActionBar().setTitle(R.string.app_database);
        }

//        Toast.makeText(getApplicationContext(), "TODO: Implementar lógica do banco", Toast.LENGTH_SHORT).show();
        PostAdapter adapter = new PostAdapter(rv_posts);
        rv_posts.setAdapter(adapter);
    }



}

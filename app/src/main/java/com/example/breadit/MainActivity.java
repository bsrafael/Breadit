package com.example.breadit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

    private final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 0;
    private final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        breaditClient = ClientFactory.GetRedditClient();
//
//        rv_posts = findViewById(R.id.rv_posts);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rv_posts.setLayoutManager(layoutManager);
//
//
//        PostAdapter adapter = new PostAdapter(breaditClient, rv_posts);
//        rv_posts.setAdapter(adapter);



//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        MY_PERMISSIONS_REQUEST_READ_STORAGE);
//
//            }
//        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE);

            }
        }

        breaditClient = ClientFactory.GetRedditClient();

        rv_posts = findViewById(R.id.rv_posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_posts.setLayoutManager(layoutManager);


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

    private void showPostsFromFeed(){
        try {
            this.getActionBar().setTitle(R.string.app_feed);
        } catch (Exception e) {
            getSupportActionBar().setTitle(R.string.app_feed);
        }

        PostAdapter adapter = new PostAdapter(breaditClient, rv_posts);
        rv_posts.setAdapter(adapter);

    }

    private void showPostsFromDatabase(){
        try {
            this.getActionBar().setTitle(R.string.app_database);
        } catch (Exception e) {
            getSupportActionBar().setTitle(R.string.app_database);
        }

        PostAdapter adapter = new PostAdapter(rv_posts);
        rv_posts.setAdapter(adapter);
    }



}

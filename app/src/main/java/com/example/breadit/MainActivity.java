package com.example.breadit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.breadit.models.ListingChild;
import com.example.breadit.models.RedditListing;
import com.example.breadit.network.ClientFactory;
import com.example.breadit.network.RedditClient;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

//    TODO: finish picture handling
//    teste async task
//
//    new DownloadImageTask(ELEMENTO IMAGE VIEW)
//                .execute("https://b.thumbs.redditmedia.com/qnbL83lgt1KzhiWhD8fP4yd6ymC4_48nRXe23zQjN8k.jpg");
//
//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }

}

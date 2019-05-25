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
    private String before;
    private String after;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        breaditClient = ClientFactory.GetSubRedditClient("Breadit");

        rv_posts = findViewById(R.id.rv_posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_posts.setLayoutManager(layoutManager);

        before = "";
        after = "";

        Call<RedditListing> call = breaditClient.get();
        call.enqueue(new Callback<RedditListing>() {
            @Override
            public void onResponse(Call<RedditListing> call, Response<RedditListing> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<RedditListing> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        final List<Post> posts = new ArrayList<>();
//        int id, int upvotes, String author, String title, String text, Uri picture, boolean savedState
        posts.add(new Post(1, 11, "u/senhor_teste1", "Meu primeiro pão :)", "Veja aqui meu primeiro pão! É um pão integral, feito esse final de semana. Levou em torno de quatro horas ao todo, contando com tempo de fermntação e tudo o mais.", "https://b.thumbs.redditmedia.com/qnbL83lgt1KzhiWhD8fP4yd6ymC4_48nRXe23zQjN8k.jpg", false));
        posts.add(new Post(2, 12, "u/senhor_teste2", "Meu segundo pão :)", "Veja aqui meu segundo pão! É um pão integral, feito esse final de semana. Levou em torno de quatro horas ao todo, contando com tempo de fermntação e tudo o mais.", "/assets/sample_bread.jpg", false));
        posts.add(new Post(3, 13, "u/senhor_teste3", "Meu terceiro pão :)", "Veja aqui meu terceiro pão! É um pão integral, feito esse final de semana. Levou em torno de quatro horas ao todo, contando com tempo de fermntação e tudo o mais.", "", false));
        posts.add(new Post(4, 14, "u/senhor_teste4", "Meu quarto pão :)", "Veja aqui meu quarto pão! É um pão integral, feito esse final de semana. Levou em torno de quatro horas ao todo, contando com tempo de fermntação e tudo o mais.", "", false));
        posts.add(new Post(5, 15, "u/senhor_teste5", "Meu quinto pão :)", "Veja aqui meu quinto pão! É um pão integral, feito esse final de semana. Levou em torno de quatro horas ao todo, contando com tempo de fermntação e tudo o mais.", "", false));
        posts.add(new Post(6, 16, "u/senhor_teste6", "Meu sexto pão :)", "Veja aqui meu sexto pão! É um pão integral, feito esse final de semana. Levou em torno de quatro horas ao todo, contando com tempo de fermntação e tudo o mais.", "", false));
        posts.add(new Post(7, 17, "u/senhor_teste7", "Meu sétimo pão :)", "Veja aqui meu sétimo pão! É um pão integral, feito esse final de semana. Levou em torno de quatro horas ao todo, contando com tempo de fermntação e tudo o mais.", "", false));
        posts.add(new Post(8, 18, "u/senhor_teste8", "Meu oitavo pão :)", "Veja aqui meu oitavo pão! É um pão integral, feito esse final de semana. Levou em torno de quatro horas ao todo, contando com tempo de fermntação e tudo o mais.", "", false));
        posts.add(new Post(9, 19, "u/senhor_teste9", "Meu nono pão :)", "Veja aqui meu nono pão! É um pão integral, feito esse final de semana. Levou em torno de quatro horas ao todo, contando com tempo de fermntação e tudo o mais.", "", false));



        PostAdapter adapter = new PostAdapter(posts);
        rv_posts.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void generateDataList(RedditListing listing) {
        final List<Post> posts = new ArrayList<>();

        before = listing.getListingData().getBefore();
        after = listing.getListingData().getAfter();

        List<ListingChild> children = listing.getListingData().getChildren();
        for (ListingChild child : children) {
            int tempId = 0; // TODO remove
            String id = child.getData().getId(); // TODO change post id to string
            int upvotes = child.getData().getScore(); // TODO change to score on code
            String author = child.getData().getAuthor();
            String title = child.getData().getTitle();
            String text = child.getData().getSelftext();
            String thumbnail = child.getData().getThumbnail(); // TODO change name in post to thumbnail
            boolean savedState = false; // TODO query from database with string id

            posts.add(new Post(tempId, upvotes, author, title, text, thumbnail, savedState));
        }

        PostAdapter adapter = new PostAdapter(posts);
        rv_posts.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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

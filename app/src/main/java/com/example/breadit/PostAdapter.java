package com.example.breadit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breadit.data.BDSQLiteHelper;
import com.example.breadit.models.ListingChild;
import com.example.breadit.models.RedditListing;
import com.example.breadit.network.DownloadImageTask;
import com.example.breadit.network.RedditClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.content.ContextCompat.startActivity;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private List<Post> posts;
    private RedditClient client;
    private String before;
    private String after;
    private int count;
    private int lastVisibleItem;
    private int totalItemCount;
    private final int visibleThreshold = 5;
    private boolean loading = false;
    private BDSQLiteHelper db;

    private Context context = null;

    public PostAdapter(RedditClient client, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.posts = new ArrayList<>();
        this.client = client;
        count = 0;
        before = "";
        after = "";

        db = new BDSQLiteHelper(recyclerView.getContext());

        getNextPage();

        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if(!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)){
                        loading = true;
                        getNextPage();
                    }
                }
            });
        }
    }

    public PostAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.posts = new ArrayList<>();
        this.client = null;
        count = 0;
        before = "";
        after = "";

        db = new BDSQLiteHelper(recyclerView.getContext());

        getSaved();
    }

    private void getNextPage() {
        Call<RedditListing> call;

        if (after == "")
            call = client.get("Breadit");
        else
            call = client.getAfter("Breadit", count, after);

        call.enqueue(new Callback<RedditListing>() {
            @Override
            public void onResponse(Call<RedditListing> call, Response<RedditListing> response) {
                generateDataList(response.body());
                loading = false;
            }

            @Override
            public void onFailure(Call<RedditListing> call, Throwable t) {
//                Toast.makeText(recyclerView.getContext(), "Something went wrong...Sorry......", Toast.LENGTH_LONG).show();
                loading = false;
            }
        });
    }

    private void generateDataList(RedditListing listing) {
        count += 25;
        before = listing.getListingData().getBefore();
        after = listing.getListingData().getAfter();

        List<ListingChild> children = listing.getListingData().getChildren();
        for (ListingChild child : children) {
            String id = child.getData().getId();
            int score = child.getData().getScore();
            String author = child.getData().getAuthor();
            String title = child.getData().getTitle();
            String text = child.getData().getSelftext();
            String image = child.getData().getUrl();
            boolean savedState = db.getPost(id) != null;

            posts.add(new Post(id, score, author, title, text, image, savedState));
        }

        notifyDataSetChanged();
    }

    private void getSaved() {
        posts = db.getAllPosts();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.postcard_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
    {
        viewHolder.setData(posts.get(position));
    }

    @Override
    public int getItemCount()
    {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Post post;
        private TextView CardName;
        private TextView CardTime;
        private TextView CardUser;
        private TextView CardText;
        private TextView CardUpvotes;
        private Switch CardSave;
        private ImageView CardPicture;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            context = itemView.getContext();

            itemView.setOnClickListener(this);
            CardName = itemView.findViewById(R.id.CardName);
            CardTime = itemView.findViewById(R.id.CardTime);
            CardUser = itemView.findViewById(R.id.CardUser);
            CardText = itemView.findViewById(R.id.CardText);
            CardUpvotes = itemView.findViewById(R.id.CardUpvotes);
            CardSave = itemView.findViewById(R.id.CardSave);
            CardPicture = itemView.findViewById(R.id.CardPicture);

            CardSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (post == null)
                        return;

                    if (isChecked) {
                        if (db.getPost(post.getId()) == null)
                            db.addPost(post);
                    }
                    else {
                        db.deletePost(post);
                    }
                    post.setSavedState(isChecked);

                }
            });
        }

        private void setData(Post post) {
            this.post = post;
            CardName.setText(post.getTitle());
            CardTime.setText(post.getTime());
            CardUser.setText(post.getAuthor());

            if(post.getText().length() > 140)
                CardText.setText(post.getText().substring(0, 140) + "..." );
            else
                CardText.setText(post.getText());

            CardUpvotes.setText(new Integer(post.getScore()).toString());
            CardSave.setChecked(post.getSavedState());
            CardPicture.setImageBitmap(null);


            if (post.getPicture() != null || post.getPicture() != "") {
                new DownloadImageTask(CardPicture)
                        .execute(post.getPicture());
                CardPicture.getLayoutParams().width = ((View)CardPicture.getParent()).getWidth();
                CardPicture.requestLayout();
            }


        }


        public void onClick(View view) {

                Post post = posts.get(getLayoutPosition());
                Intent intent = new Intent(context, Content.class);
                intent.putExtra("post", post);

                startActivity(context, intent, null);

        }


    }


}


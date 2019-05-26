package com.example.breadit;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breadit.models.ListingChild;
import com.example.breadit.models.RedditListing;
import com.example.breadit.network.RedditClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public PostAdapter(RedditClient client, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.posts = new ArrayList<>();
        this.client = client;
        count = 0;
        before = "";
        after = "";

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
                Toast.makeText(recyclerView.getContext(), "Something went wrong...Sorry......" + t.getMessage() + after, Toast.LENGTH_SHORT).show();

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
        private TextView CardName;
        private TextView CardTime;
        private TextView CardUser;
        private TextView CardText;
        private TextView CardUpvotes;
        private Switch CardSave;
        private ImageView CardPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            CardName = itemView.findViewById(R.id.CardName);
            CardTime = itemView.findViewById(R.id.CardTime);
            CardUser = itemView.findViewById(R.id.CardUser);
            CardText = itemView.findViewById(R.id.CardText);
            CardUpvotes = itemView.findViewById(R.id.CardUpvotes);
            CardSave = itemView.findViewById(R.id.CardSave);
            CardPicture = itemView.findViewById(R.id.CardPicture);


        }

        private void setData(Post post) {
            CardName.setText(post.getTitle());
            CardTime.setText(post.getTime());
            CardUser.setText(post.getAuthor());
            CardText.setText(post.getText());
            CardUpvotes.setText(new Integer(post.getUpvotes()).toString());
            CardSave.setChecked(post.getSavedState());

            CardPicture.setImageBitmap(null);

        ;}


        public void onClick(View view) {
            Toast.makeText(view.getContext(),"Você selecionou "
                    + posts.get(getLayoutPosition()).getTitle(),Toast.LENGTH_LONG). show();
        } }
    }


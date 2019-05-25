package com.example.breadit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
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


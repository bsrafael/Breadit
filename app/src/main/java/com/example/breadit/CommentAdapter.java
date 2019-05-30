package com.example.breadit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.breadit.models.ListingChild;
import com.example.breadit.models.RedditListing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO: comment handling [2]
// possibly useful -> https://www.itworld.com/article/2696636/how-to-create-nested-comments-in-java---android.html
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private Context context = null;
    private List<Comment> comments;

    public CommentAdapter(RedditListing rawComments) {
        comments = new ArrayList<Comment>();

        for (ListingChild rawComment : rawComments.getListingData().getChildren()) {
            String id = rawComment.getData().getId();
            //String time = rawChild.getData().
            String time = "Placeholder";
            String author = rawComment.getData().getAuthor();
            String text = rawComment.getData().getBody();
            List<Comment> children = getChildComments(rawComment);
            comments.add(new Comment(id, "", time, author, text, children));
        }

        notifyDataSetChanged();
    }

    private List<Comment> getChildComments(ListingChild rawComment) {
        String fatherId = rawComment.getData().getId();
        List<Comment> result = new ArrayList<Comment>();

        for (ListingChild rawChild : rawComment.getData().getReplies().getListingData().getChildren()) {
            String id = rawChild.getData().getId();
            //String time = rawChild.getData().
            String time = "Placeholder";
            String author = rawChild.getData().getAuthor();
            String text = rawChild.getData().getBody();
            List<Comment> children = getChildComments(rawChild);
            result.add(new Comment(id, fatherId, time, author, text, children));
        }

        return result;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder viewHolder, int position) {
        viewHolder.setData(comments.get(position));
    }

    @Override
    public int getItemCount()
    {
        return comments.size();
    }





    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Comment comment;
        private TextView CommentUser;
        private TextView CommentBody;
        private TextView CommentTime;
        private TextView CommentScore;
        private RecyclerView CommentChildren;




        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            context = itemView.getContext();

            CommentUser = itemView.findViewById(R.id.comment_user);
            CommentBody = itemView.findViewById(R.id.comment_body);
            CommentScore = itemView.findViewById(R.id.comment_score);
            CommentTime = itemView.findViewById(R.id.comment_time);
            CommentChildren = itemView.findViewById(R.id.rv_comment_children);

        }

        private void setData(Comment comment) {


        }


        public void onClick(View view) {

        }
    }
}

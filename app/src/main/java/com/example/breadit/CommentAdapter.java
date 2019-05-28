package com.example.breadit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// TODO: comment handling [2]
// possibly useful -> https://www.itworld.com/article/2696636/how-to-create-nested-comments-in-java---android.html
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private Context context = null;
    private List<Comment> comments;


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

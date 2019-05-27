package com.example.breadit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breadit.network.DownloadImageTask;

public class Content extends AppCompatActivity {

    private Post post;

    private TextView ContentTitle;
    private TextView ContentText;
    private ImageView ContentPicture;
    private TextView ContentAuthor;
    private TextView ContentScore;
    private Switch ContentSavedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Toast.makeText(this.getApplicationContext(),  "entrei no Content.java", Toast.LENGTH_SHORT);
        this.post = (Post)bundle.getSerializable("post");
        findViews();
        setContent();




    }


    private void findViews() {
        ContentTitle = (TextView)findViewById( R.id.ContentTitle );
        ContentText = (TextView)findViewById( R.id.ContentText );
        ContentPicture = (ImageView)findViewById( R.id.ContentPicture );
        ContentAuthor = (TextView)findViewById( R.id.ContentAuthor);
        ContentScore = (TextView)findViewById( R.id.ContentScore );
        ContentSavedState = (Switch)findViewById( R.id.ContentSavedState );
    }

    private void setContent(){
        if (post.getTitle() != null || post.getTitle() != "")
            ContentTitle.setText(post.getTitle());
        else
            ContentTitle.setVisibility(View.GONE);

        if (post.getText() != null || post.getText() != "")
            ContentText.setText(post.getText());
        else
            ContentText.setVisibility(View.GONE);

        if (post.getAuthor() != null || post.getAuthor() != "")
            ContentAuthor.setText(post.getAuthor());
        else
            ContentAuthor.setVisibility(View.GONE);

        ContentScore.setText( String.valueOf(post.getScore()) );

        ContentSavedState.setChecked(post.getSavedState());

        if (post.getPicture() != null || post.getPicture() != "") {
            new DownloadImageTask(ContentPicture)
                    .execute(post.getPicture());
            ContentPicture.getLayoutParams().width = ((View)ContentPicture.getParent()).getWidth();
            ContentPicture.requestLayout();
        }
        else {
            ContentPicture.setVisibility(View.GONE);
        }
    }
}

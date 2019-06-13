package com.example.breadit.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.breadit.Post;

import java.util.ArrayList;
import java.util.List;

public class BDSQLiteHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Breadit";
    private static final int DATABASE_VERSION = 2;

    private static final String TABELA_POSTS = "Posts";
    private static final String POSTS_ID = "Id";
    private static final String POSTS_SCORE = "Score";
    private static final String POSTS_TIME = "Time";
    private static final String POSTS_AUTHOR = "Author";
    private static final String POSTS_TITLE = "Title";
    private static final String POSTS_TEXT = "Text";
    private static final String POSTS_PICTURE = "Picture";
    private static final String POSTS_PICTURE_LOCAL = "PictureLocal";

    public BDSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPosts = "CREATE TABLE Posts(" +
                "Id TEXT PRIMARY KEY," +
                "Score INTEGER," +
                "Time TEXT," +
                "Author TEXT," +
                "Title TEXT," +
                "Text TEXT," +
                "Picture TEXT," +
                "PictureLocal TEXT)";

        db.execSQL(createPosts);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Posts");
        this.onCreate(db);
    }

    public void addPost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(POSTS_ID, post.getId());
        values.put(POSTS_SCORE, post.getScore());
        values.put(POSTS_TIME, post.getTime());
        values.put(POSTS_AUTHOR, post.getAuthor());
        values.put(POSTS_TITLE, post.getTitle());
        values.put(POSTS_TEXT, post.getText());
        values.put(POSTS_PICTURE, post.getPicture());
        values.put(POSTS_PICTURE_LOCAL, post.getPictureLocal());
        db.insert(TABELA_POSTS , null, values);
        db.close();
    }

    public int deletePost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABELA_POSTS,
                POSTS_ID +" = ?",
                new String[] {
                        post.getId()
                });
        db.close();
        return i;
    }

    private Post cursorToPost(Cursor cursor) {
        String id = cursor.getString(0);
        int score = Integer.parseInt(cursor.getString(1));
        String time = cursor.getString(2);
        String author = cursor.getString(3);
        String title = cursor.getString(4);
        String text = cursor.getString(5);
        String picture = cursor.getString(6);
        String pictureLocal = cursor.getString(7);
        return new Post(id, score, author, title, text, picture, true, pictureLocal);
    }

    public List<Post> getAllPosts() {
        ArrayList<Post> listaLivros = new ArrayList<Post>();
        String query = "SELECT * FROM " + TABELA_POSTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int i = 0;

        if (cursor.moveToFirst()) {
            do {
                Post post = cursorToPost(cursor);
                listaLivros.add(post);
                i++;
            } while (cursor.moveToNext());
        }

        System.out.println(i);
        Toast.makeText(this.context, "i = "+i , Toast.LENGTH_SHORT );


        return listaLivros;
    }

    public Post getPost(String id) {
        String query = "SELECT * FROM " + TABELA_POSTS +
                " WHERE Id = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[] { id });

        if (cursor.moveToFirst())
            return cursorToPost(cursor);

        return null;
    }
}

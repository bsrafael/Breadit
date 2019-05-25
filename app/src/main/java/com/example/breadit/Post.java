package com.example.breadit;

import android.net.Uri;
import java.io.Serializable;

public class Post implements Serializable {


    private int id;
    private int upvotes;
    private String time;
    private String author;
    private String title;
    private String text;
    private String picture;
    private boolean savedState;

    public Post(){

    }

    public Post(int id, int upvotes, String author, String title, String text, String picture, boolean savedState) {
        this.id = id;
        this.upvotes = upvotes;
        this.author = author;
        this.title = title;
        this.text = text;
        this.picture = picture;
        this.savedState = savedState;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean getSavedState() {
        return savedState;
    }

    public void setSavedState(boolean savedState) {
        this.savedState = savedState;
    }

}

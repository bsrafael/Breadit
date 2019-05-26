package com.example.breadit;

import java.io.Serializable;

public class Post implements Serializable {


    private String id;
    private int score;
    private String time;
    private String author;
    private String title;
    private String text;
    private String picture;
    private boolean savedState;

    public Post(){

    }

    public Post(String id, int score, String author, String title, String text, String picture, boolean savedState) {
        this.id = id;
        this.score = score;
        this.author = author;
        this.title = title;
        this.text = text;
        this.picture = picture;
        this.savedState = savedState;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

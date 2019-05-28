package com.example.breadit;

import java.io.Serializable;
import java.util.List;

// TODO: comment handling [1]
public class Comment implements Serializable {
    private String id;
    private String father_id;
    private String time;
    private String author;
    private String text;
    private List<Comment> children;
    private int depth;
    private int childCount;


    public Comment() {

    }

    public Comment(String id, String father_id, String time, String author, String text, List<Comment> children) {
        this.id = id;
        this.father_id = father_id;
        this.time = time;
        this.author = author;
        this.text = text;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFather_id() {
        return father_id;
    }

    public void setFather_id(String father_id) {
        this.father_id = father_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }






}

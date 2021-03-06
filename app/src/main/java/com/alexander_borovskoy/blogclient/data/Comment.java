package com.alexander_borovskoy.blogclient.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Comment extends RealmObject {

    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("datePublic")
    @Expose
    private String datePublic;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("text")
    @Expose
    private String text;
    private long postId;

    public Comment() {
    }

    public Comment(String author, String datePublic, long id, String text) {
        this.author = author;
        this.datePublic = datePublic;
        this.id = id;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDatePublic() {
        return datePublic;
    }

    public void setDatePublic(String datePublic) {
        this.datePublic = datePublic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}

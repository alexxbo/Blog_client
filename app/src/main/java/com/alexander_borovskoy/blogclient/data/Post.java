package com.alexander_borovskoy.blogclient.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Post extends RealmObject {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("datePublic")
    @Expose
    private String datePublic;

    public Post() {
    }

    public Post(long id, String title, String text, String datePublic) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.datePublic = datePublic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDatePublic() {
        return datePublic;
    }

    public void setDatePublic(String datePublic) {
        this.datePublic = datePublic;
    }
}

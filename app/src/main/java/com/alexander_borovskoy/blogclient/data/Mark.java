package com.alexander_borovskoy.blogclient.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mark {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;

    public Mark(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

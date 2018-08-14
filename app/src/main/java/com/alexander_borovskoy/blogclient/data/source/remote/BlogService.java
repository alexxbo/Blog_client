package com.alexander_borovskoy.blogclient.data.source.remote;

import com.alexander_borovskoy.blogclient.data.Comment;
import com.alexander_borovskoy.blogclient.data.Mark;
import com.alexander_borovskoy.blogclient.data.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BlogService {

    String API_BLOG_URL = "http://fed-blog.herokuapp.com";

    @GET("/api/v1/posts")
    Call<List<Post>> getAllPosts();

//    @GET("/api/v1/posts")
//    Call<List<Post>> getAllPosts(@Query("page") int page, @Query("size") int postCounts);

    @GET("/api/v1/posts/{id}/marks")
    Call<List<Mark>> getPostMarks(@Path("id") long postId);

    @GET("/api/v1/comments/posts/{id}")
    Call<List<Comment>> getPostComments(@Path("id") long postId);

    @GET("/api/v1/posts/{id}")
    Call<Post> getPostById(@Path("id") long id);
}

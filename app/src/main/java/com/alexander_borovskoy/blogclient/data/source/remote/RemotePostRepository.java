package com.alexander_borovskoy.blogclient.data.source.remote;

import android.support.annotation.NonNull;

import com.alexander_borovskoy.blogclient.data.Comment;
import com.alexander_borovskoy.blogclient.data.Mark;
import com.alexander_borovskoy.blogclient.data.Post;
import com.alexander_borovskoy.blogclient.data.source.PostsDataSource;
import com.alexander_borovskoy.blogclient.utils.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemotePostRepository implements PostsDataSource {

    private static final String TAG = "LOG TAG";
    private AppExecutors mExecutors;
    private BlogService mBlogService;

    public RemotePostRepository(AppExecutors executors, BlogService blogService) {
        mBlogService = blogService;
        mExecutors = executors;
    }

    @Override
    public void getAllPosts(@NonNull final LoadPostsCallback callback) {
        mBlogService.getAllPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    callback.onPostsLoaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getPost(long postId, @NonNull final LoadPostCallback callback) {
        mBlogService.getPostById(postId).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    callback.onPostLoaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getPostMarks(long postId, @NonNull final LoadPostMarksCallback callback) {
        mBlogService.getPostMarks(postId).enqueue(new Callback<List<Mark>>() {
            @Override
            public void onResponse(Call<List<Mark>> call, Response<List<Mark>> response) {
                if (response.isSuccessful()) {
                    callback.onPostMarksLoaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Mark>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getPostComments(long postId, @NonNull final LoadPostCommentsCallback callback) {
        mBlogService.getPostComments(postId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    callback.onPostCommentsLoaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }
}

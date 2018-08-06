package com.alexander_borovskoy.blogclient;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alexander_borovskoy.blogclient.db.Comment;
import com.alexander_borovskoy.blogclient.db.Mark;
import com.alexander_borovskoy.blogclient.db.Post;
import com.alexander_borovskoy.blogclient.source.BlogService;
import com.alexander_borovskoy.blogclient.source.DataSource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRepository implements DataSource{

    private static final Object LOCK = new Object();
    private static final String TAG = "LOG TAG";
    private static PostRepository sInstance;
//    private AppExecutors mExecutors;
    private BlogService mBlogService;

    private PostRepository(/*AppExecutors mExecutors*/) {
//        this.mExecutors = mExecutors;
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BlogService.API_BLOG_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mBlogService = retrofit.create(BlogService.class);
    }

    public synchronized static PostRepository getInstance(
//        AppExecutors executors
    ){
        Log.d(TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new PostRepository(/*executors*/);
                Log.d(TAG, "Made new repository");
            }
        }
        return sInstance;
    }


    @Override
    public void getAllPosts(@NonNull final LoadPostsCallback callback) {
        mBlogService.getAllPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.i(TAG, "onResponse: code = " + response.code());
                if (response.isSuccessful()){
                    callback.onPostsLoaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                callback.onDataNotAvailable();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void getPost(long postId, @NonNull final GetPostsCallback callback) {
        mBlogService.getPostById(postId).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()){
                    callback.onPostsLoaded(response.body());
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
                if (response.isSuccessful()){
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

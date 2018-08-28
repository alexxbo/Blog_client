package com.alexander_borovskoy.blogclient.data.source;

import android.support.annotation.NonNull;

import com.alexander_borovskoy.blogclient.data.Comment;
import com.alexander_borovskoy.blogclient.data.Mark;
import com.alexander_borovskoy.blogclient.data.Post;

import java.util.List;

public interface PostsDataSource {

    interface LoadPostsCallback {

        void onPostsLoaded(List<Post> postList);

        void onDataNotAvailable();
    }

    interface LoadPostCallback {

        void onPostLoaded(Post post);

        void onDataNotAvailable();
    }

    interface LoadPostMarksCallback {
        void onPostMarksLoaded(List<Mark> markList);

        void onDataNotAvailable();
    }

    interface LoadPostCommentsCallback {
        void onPostCommentsLoaded(List<Comment> commentList);

        void onDataNotAvailable();
    }

    void getAllPosts(@NonNull LoadPostsCallback callback);

    void getPost(long postId, @NonNull LoadPostCallback callback);

    void getPostMarks(long postId, @NonNull LoadPostMarksCallback callback);

    void getPostComments(long postId, @NonNull LoadPostCommentsCallback callback);
}

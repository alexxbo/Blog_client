package com.alexander_borovskoy.blogclient.source;

import android.support.annotation.NonNull;

import com.alexander_borovskoy.blogclient.db.Comment;
import com.alexander_borovskoy.blogclient.db.Mark;
import com.alexander_borovskoy.blogclient.db.Post;

import java.util.List;

public interface DataSource {

    interface LoadPostsCallback {

        void onPostsLoaded(List<Post> postList);

        void onDataNotAvailable();
    }

    interface GetPostsCallback {

        void onPostsLoaded(Post post);

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

    void getPost(long postId, @NonNull GetPostsCallback callback);

    void getPostMarks(long postId, @NonNull LoadPostMarksCallback callback);

    void getPostComments(long postId, @NonNull LoadPostCommentsCallback callback);
}

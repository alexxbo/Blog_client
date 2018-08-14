package com.alexander_borovskoy.blogclient.ui.postlist;

import android.support.annotation.NonNull;

import com.alexander_borovskoy.blogclient.BasePresenter;
import com.alexander_borovskoy.blogclient.BaseView;
import com.alexander_borovskoy.blogclient.data.Post;

import java.util.List;

public interface PostListContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showPosts(List<Post> posts);

        void showPostDetails(Long postId);

        void showLoadingPostsError();

        void showNoPosts();

    }

    interface Presenter extends BasePresenter {

        void updatePosts();

        void openPostDetails(@NonNull Post requestedPost);
    }
}

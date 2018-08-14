package com.alexander_borovskoy.blogclient.ui.postdetails;

import android.support.annotation.NonNull;

import com.alexander_borovskoy.blogclient.BasePresenter;
import com.alexander_borovskoy.blogclient.BaseView;
import com.alexander_borovskoy.blogclient.data.Comment;
import com.alexander_borovskoy.blogclient.data.Mark;
import com.alexander_borovskoy.blogclient.data.Post;
import com.alexander_borovskoy.blogclient.ui.postlist.PostListContract;

import java.util.List;

public interface PostDetailContract  {

    interface View extends BaseView<PostListContract.Presenter> {

        void setLoadingIndicator(boolean active);

        void showPost(Post post);

        void showPostMarks(Mark mark);

        void showPostComments(List<Comment> comments);

        void showLoadingPostError();

        void showLoadingPostCommentsError();

        void showNoComments();

    }

    interface Presenter extends BasePresenter {

        void updatePost();

        void updatePostComments();

    }
}

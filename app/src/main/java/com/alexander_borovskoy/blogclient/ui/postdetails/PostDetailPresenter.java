package com.alexander_borovskoy.blogclient.ui.postdetails;

import android.support.annotation.NonNull;

import com.alexander_borovskoy.blogclient.data.Comment;
import com.alexander_borovskoy.blogclient.data.Mark;
import com.alexander_borovskoy.blogclient.data.Post;
import com.alexander_borovskoy.blogclient.data.source.PostsDataSource;

import java.util.List;

public class PostDetailPresenter implements PostDetailContract.Presenter {

    private static final String TAG = "PostDetailPresenter";
    private final PostsDataSource mRepo;
    private final PostDetailContract.View mPostDetailView;
    private final Long mPostId;

    public PostDetailPresenter(@NonNull Long mPostId, @NonNull PostsDataSource mRepo, @NonNull PostDetailContract.View mPostDetailView) {
        this.mRepo = mRepo;
        this.mPostId = mPostId;
        this.mPostDetailView = mPostDetailView;
        mPostDetailView.setPresenter(this);
    }

    @Override
    public void updatePost() {
        mRepo.getPost(mPostId, new PostsDataSource.LoadPostCallback() {
            @Override
            public void onPostLoaded(Post post) {
                mPostDetailView.showPost(post);
                updatePostMarks();
                updatePostComments();
            }

            @Override
            public void onDataNotAvailable() {
                mPostDetailView.showLoadingPostError();
            }
        });
    }

    @Override
    public void updatePostMarks() {
        mRepo.getPostMarks(mPostId, new PostsDataSource.LoadPostMarksCallback() {
            @Override
            public void onPostMarksLoaded(List<Mark> markList) {
                if (markList.isEmpty()) {
                    //Show no Mark
                } else {
                    mPostDetailView.showPostMarks(markList);
                }
            }

            @Override
            public void onDataNotAvailable() {
                //Show loading post mark error
            }
        });
    }

    @Override
    public void updatePostComments() {
        mPostDetailView.setLoadingIndicator(true);
        mRepo.getPostComments(mPostId, new PostsDataSource.LoadPostCommentsCallback() {
            @Override
            public void onPostCommentsLoaded(List<Comment> commentList) {
                mPostDetailView.setLoadingIndicator(false);
                if (commentList.isEmpty()) {
                    mPostDetailView.showNoComments();
                } else {
                    mPostDetailView.showPostComments(commentList);
                }
            }

            @Override
            public void onDataNotAvailable() {
                mPostDetailView.setLoadingIndicator(false);
                mPostDetailView.showLoadingPostCommentsError();
            }
        });
    }

    @Override
    public void onViewCreated() {
        updatePost();
    }

    @Override
    public void onViewDestroyed() {
    }
}

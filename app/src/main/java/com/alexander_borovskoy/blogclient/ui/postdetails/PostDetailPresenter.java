package com.alexander_borovskoy.blogclient.ui.postdetails;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alexander_borovskoy.blogclient.data.Comment;
import com.alexander_borovskoy.blogclient.data.Mark;
import com.alexander_borovskoy.blogclient.data.Post;
import com.alexander_borovskoy.blogclient.data.source.PostRepository;
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
        Log.d(TAG, "updatePost: ");

        mPostDetailView.setLoadingIndicator(true);
        mRepo.getPost(mPostId, new PostsDataSource.GetPostsCallback() {
            @Override
            public void onPostsLoaded(Post post) {
                Log.d(TAG, "onPostsLoaded: postTitle - " + post.getTitle());
                mPostDetailView.setLoadingIndicator(false);
                mPostDetailView.showPost(post);
                updatePostMarks();
                updatePostComments();
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "onDataNotAvailable: ");
                mPostDetailView.setLoadingIndicator(false);
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
        mRepo.getPostComments(mPostId, new PostsDataSource.LoadPostCommentsCallback() {
            @Override
            public void onPostCommentsLoaded(List<Comment> commentList) {
                if (commentList.isEmpty()) {
                    mPostDetailView.showNoComments();
                } else {
                    mPostDetailView.showPostComments(commentList);
                }
            }

            @Override
            public void onDataNotAvailable() {
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

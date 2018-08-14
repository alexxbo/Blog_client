package com.alexander_borovskoy.blogclient.ui.postlist;

import android.support.annotation.NonNull;
import android.util.Log;

import com.alexander_borovskoy.blogclient.data.Post;
import com.alexander_borovskoy.blogclient.data.source.PostRepository;
import com.alexander_borovskoy.blogclient.data.source.PostsDataSource;

import java.util.List;


public class PostsPresenter implements PostListContract.Presenter {

    private static final String TAG = "PostsPresenter";
    private final PostRepository mRepo;
    private final PostListContract.View mPostsView;


    public PostsPresenter(@NonNull PostRepository repository, @NonNull PostListContract.View postsView) {
        this.mRepo = repository;
        this.mPostsView = postsView;
        mPostsView.setPresenter(this);
    }


    @Override
    public void updatePosts() {
        Log.d(TAG, "updatePosts: ");

        mPostsView.setLoadingIndicator(true);
        mRepo.getAllPosts(new PostsDataSource.LoadPostsCallback() {
            @Override
            public void onPostsLoaded(List<Post> postList) {
                Log.d(TAG, "onPostsLoaded: " + postList.size());
                    mPostsView.setLoadingIndicator(false);
                if (postList.isEmpty()) {
                    mPostsView.showNoPosts();
                } else {
                    mPostsView.showPosts(postList);
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "onDataNotAvailable: ");
                mPostsView.setLoadingIndicator(false);
                mPostsView.showLoadingPostsError();
            }
        });
    }

    @Override
    public void openPostDetails(@NonNull Post requestedPost) {
        mPostsView.showPostDetails(requestedPost.getId());
    }

    @Override
    public void onViewCreated() {
        updatePosts();
    }

    @Override
    public void onViewDestroyed() {
    }
}

package com.alexander_borovskoy.blogclient.ui.postdetails.di;

import com.alexander_borovskoy.blogclient.data.source.PostsDataSource;
import com.alexander_borovskoy.blogclient.ui.postdetails.CommentAdapter;
import com.alexander_borovskoy.blogclient.ui.postdetails.PostDetailContract;
import com.alexander_borovskoy.blogclient.ui.postdetails.PostDetailFragment;
import com.alexander_borovskoy.blogclient.ui.postdetails.PostDetailPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PostDetailModule {

    private final PostDetailFragment fragment;
    private final long postId;

    public PostDetailModule(PostDetailFragment fragment, long postId) {
        this.fragment = fragment;
        this.postId = postId;
    }

    @PostDetailScope
    @Provides
    CommentAdapter provideCommentAdapter() {
        return new CommentAdapter();
    }

    @PostDetailScope
    @Provides
    PostDetailContract.Presenter providePostDetailPresenter(PostsDataSource repository) {
        return new PostDetailPresenter(postId, repository, fragment);
    }
}

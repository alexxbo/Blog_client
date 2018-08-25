package com.alexander_borovskoy.blogclient.ui.postlist.di;

import com.alexander_borovskoy.blogclient.data.source.PostsDataSource;
import com.alexander_borovskoy.blogclient.ui.postlist.PostAdapter;
import com.alexander_borovskoy.blogclient.ui.postlist.PostListContract;
import com.alexander_borovskoy.blogclient.ui.postlist.PostListFragment;
import com.alexander_borovskoy.blogclient.ui.postlist.PostsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PostListModule {

    private final PostListFragment fragment;

    public PostListModule(PostListFragment fragment) {
        this.fragment = fragment;
    }

    @PostListScope
    @Provides
    PostListContract.Presenter providePostsPresenter(PostsDataSource repository) {
        return new PostsPresenter(repository, fragment);
    }

    @PostListScope
    @Provides
    PostAdapter providePostAdapter() {
        return new PostAdapter(fragment);
    }
}

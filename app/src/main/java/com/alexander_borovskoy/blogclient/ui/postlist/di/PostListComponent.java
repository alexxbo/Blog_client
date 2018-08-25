package com.alexander_borovskoy.blogclient.ui.postlist.di;

import com.alexander_borovskoy.blogclient.ui.postlist.PostListFragment;

import dagger.Subcomponent;

@PostListScope
@Subcomponent(modules = PostListModule.class)
public interface PostListComponent {
    void inject(PostListFragment fragment);
}

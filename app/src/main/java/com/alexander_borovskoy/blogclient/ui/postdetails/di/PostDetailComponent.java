package com.alexander_borovskoy.blogclient.ui.postdetails.di;

import com.alexander_borovskoy.blogclient.ui.postdetails.PostDetailFragment;

import dagger.Subcomponent;

@PostDetailScope
@Subcomponent(modules = PostDetailModule.class)
public interface PostDetailComponent {

    void inject(PostDetailFragment fragment);
}

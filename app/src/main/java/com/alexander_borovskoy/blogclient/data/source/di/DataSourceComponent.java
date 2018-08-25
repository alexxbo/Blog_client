package com.alexander_borovskoy.blogclient.data.source.di;

import com.alexander_borovskoy.blogclient.ui.postdetails.di.PostDetailComponent;
import com.alexander_borovskoy.blogclient.ui.postdetails.di.PostDetailModule;
import com.alexander_borovskoy.blogclient.ui.postlist.di.PostListComponent;
import com.alexander_borovskoy.blogclient.ui.postlist.di.PostListModule;

import dagger.Subcomponent;

@DataSourceScope
@Subcomponent(modules = {BlogServiceModule.class, DataSourceModule.class})
public interface DataSourceComponent {

    PostListComponent postListComponent(PostListModule postListModule);

    PostDetailComponent postDetailComponent(PostDetailModule postDetailModule);
}

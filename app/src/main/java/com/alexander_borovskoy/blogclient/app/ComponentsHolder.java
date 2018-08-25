package com.alexander_borovskoy.blogclient.app;

import android.content.Context;

import com.alexander_borovskoy.blogclient.app.di.AppComponent;
import com.alexander_borovskoy.blogclient.app.di.AppModule;
import com.alexander_borovskoy.blogclient.app.di.DaggerAppComponent;
import com.alexander_borovskoy.blogclient.data.source.di.BlogServiceModule;
import com.alexander_borovskoy.blogclient.data.source.di.DataSourceComponent;
import com.alexander_borovskoy.blogclient.data.source.di.DataSourceModule;
import com.alexander_borovskoy.blogclient.ui.postdetails.PostDetailFragment;
import com.alexander_borovskoy.blogclient.ui.postdetails.di.PostDetailComponent;
import com.alexander_borovskoy.blogclient.ui.postdetails.di.PostDetailModule;
import com.alexander_borovskoy.blogclient.ui.postlist.PostListFragment;
import com.alexander_borovskoy.blogclient.ui.postlist.di.PostListComponent;
import com.alexander_borovskoy.blogclient.ui.postlist.di.PostListModule;

public class ComponentsHolder {

    private AppComponent appComponent;
    private DataSourceComponent dataSourceComponent;
    private PostListComponent postListComponent;
    private PostDetailComponent postDetailComponent;

    public ComponentsHolder(Context appContext) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(appContext))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public DataSourceComponent getDataSourceComponent() {
        if (dataSourceComponent == null) {
            dataSourceComponent = appComponent
                    .dataSourceComponent(new DataSourceModule(),
                            new BlogServiceModule());
        }

        return dataSourceComponent;
    }

    public void releaseDataSourceComponent() {
        dataSourceComponent = null;
    }

    public PostListComponent getPostListComponent(PostListFragment fragment) {
        if (postListComponent == null) {
            postListComponent = dataSourceComponent.postListComponent(new PostListModule(fragment));

        }
        return postListComponent;
    }

    public void releasePostListComponent() {
        postListComponent = null;
    }

    public PostDetailComponent getPostDetailComponent(PostDetailFragment fragment, long postId) {
        if (postDetailComponent == null) {
            postDetailComponent = dataSourceComponent
                    .postDetailComponent(new PostDetailModule(fragment, postId));
        }
        return postDetailComponent;
    }

    public void releasePostDetailComponent() {
        postDetailComponent = null;
    }
}

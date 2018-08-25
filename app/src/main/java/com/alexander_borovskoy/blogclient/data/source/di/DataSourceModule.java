package com.alexander_borovskoy.blogclient.data.source.di;

import com.alexander_borovskoy.blogclient.data.source.PostRepository;
import com.alexander_borovskoy.blogclient.data.source.PostsDataSource;
import com.alexander_borovskoy.blogclient.data.source.remote.BlogService;
import com.alexander_borovskoy.blogclient.utils.AppExecutors;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourceModule {

    private static final int THREAD_COUNT = 3;

    @DataSourceScope
    @Provides
    PostsDataSource providePostRepository(AppExecutors appExecutors, BlogService blogService){
        return new PostRepository(appExecutors, blogService);
    }

    @Provides
    @DataSourceScope
    AppExecutors provideAppExecutors(){
        return new AppExecutors(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }
}

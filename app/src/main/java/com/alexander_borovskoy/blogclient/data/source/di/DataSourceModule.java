package com.alexander_borovskoy.blogclient.data.source.di;

import com.alexander_borovskoy.blogclient.data.source.PostRepository;
import com.alexander_borovskoy.blogclient.data.source.PostsDataSource;
import com.alexander_borovskoy.blogclient.data.source.local.LocalPostRepository;
import com.alexander_borovskoy.blogclient.data.source.remote.BlogService;
import com.alexander_borovskoy.blogclient.data.source.remote.RemotePostRepository;
import com.alexander_borovskoy.blogclient.utils.AppExecutors;

import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class DataSourceModule {

    private static final int THREAD_COUNT = 3;

    @DataSourceScope
    @Provides
    PostsDataSource providePostRepository(RemotePostRepository remoteRepository,
                                          LocalPostRepository localRepository) {
        return new PostRepository(remoteRepository, localRepository);
    }

    @DataSourceScope
    @Provides
    AppExecutors provideAppExecutors() {
        return new AppExecutors(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }

    @DataSourceScope
    @Provides
    RemotePostRepository provideRemotePostRepository(AppExecutors appExecutors,
                                                     BlogService blogService) {
        return new RemotePostRepository(appExecutors, blogService);
    }

    @DataSourceScope
    @Provides
    LocalPostRepository provideLocalPostRepository(Realm realm) {
        return new LocalPostRepository(realm);
    }
}

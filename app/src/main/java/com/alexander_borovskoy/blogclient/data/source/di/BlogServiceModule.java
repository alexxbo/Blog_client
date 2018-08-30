package com.alexander_borovskoy.blogclient.data.source.di;

import com.alexander_borovskoy.blogclient.data.source.remote.BlogService;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class BlogServiceModule {

    private static final int READ_TIMEOUT = 60;
    private static final int CONNECT_TIMEOUT = 15;

    @DataSourceScope
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @DataSourceScope
    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BlogService.API_BLOG_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @DataSourceScope
    @Provides
    public BlogService provideBlogService(Retrofit retrofit) {
        return retrofit.create(BlogService.class);
    }
}

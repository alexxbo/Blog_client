package com.alexander_borovskoy.blogclient.app.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alexander_borovskoy.blogclient.utils.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context appContext;

    public AppModule(@NonNull Context appContext) {
        this.appContext = appContext;
    }

    @Singleton
    @Provides
    Context provideAppContext() {
        return appContext;
    }
}

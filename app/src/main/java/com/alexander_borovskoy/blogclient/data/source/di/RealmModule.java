package com.alexander_borovskoy.blogclient.data.source.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class RealmModule {

    public RealmModule(Context context) {
        Realm.init(context);
    }

    @DataSourceScope
    @Provides
    Realm provideRealm(RealmConfiguration configuration){
        return Realm.getInstance(configuration);
    }

    @DataSourceScope
    @Provides
    RealmConfiguration provideRealmConfiguration(){
        return new RealmConfiguration.Builder()
                .name("posts.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
    }
}

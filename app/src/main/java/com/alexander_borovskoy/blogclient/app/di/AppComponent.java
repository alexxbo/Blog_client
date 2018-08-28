package com.alexander_borovskoy.blogclient.app.di;

import com.alexander_borovskoy.blogclient.data.source.di.BlogServiceModule;
import com.alexander_borovskoy.blogclient.data.source.di.DataSourceComponent;
import com.alexander_borovskoy.blogclient.data.source.di.DataSourceModule;
import com.alexander_borovskoy.blogclient.data.source.di.RealmModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    DataSourceComponent dataSourceComponent(DataSourceModule dataSourceModule,
                                            BlogServiceModule blogServiceModule,
                                            RealmModule realmModule);
}

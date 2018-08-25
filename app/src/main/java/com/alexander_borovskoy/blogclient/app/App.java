package com.alexander_borovskoy.blogclient.app;

import android.app.Application;

public class App extends Application {

    private static App instance;
    private ComponentsHolder componentsHolder;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        componentsHolder = new ComponentsHolder(this);
    }

    public static App getInstance() {
        return instance;
    }

    public ComponentsHolder getComponentsHolder() {
        return componentsHolder;
    }

}

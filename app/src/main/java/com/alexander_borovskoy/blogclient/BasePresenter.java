package com.alexander_borovskoy.blogclient;

public interface BasePresenter<T> {

    void onViewCreateed();

    void onViewDestroyed();
}

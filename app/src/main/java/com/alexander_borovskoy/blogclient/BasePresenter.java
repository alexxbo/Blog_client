package com.alexander_borovskoy.blogclient;

public interface BasePresenter<T> {

    void onViewCreated();

    void onViewDestroyed();

}

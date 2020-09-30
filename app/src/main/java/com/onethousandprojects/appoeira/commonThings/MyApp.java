package com.onethousandprojects.appoeira.commonThings;

import android.app.Application;

public class MyApp extends Application {
    private static MyApp instance;
    public static MyApp getInstance() {
        return instance;
    }
    public static MyApp getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}

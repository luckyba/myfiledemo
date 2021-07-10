package com.luckyba.myfiledemo.app;

import android.app.Application;

public class MyFileApplication extends Application {
    private static MyFileApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static MyFileApplication getInstance() {
        return application;
    }
}

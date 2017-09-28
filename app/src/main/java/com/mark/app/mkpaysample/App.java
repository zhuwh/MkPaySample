package com.mark.app.mkpaysample;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by zhuwh on 2017/9/28.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}

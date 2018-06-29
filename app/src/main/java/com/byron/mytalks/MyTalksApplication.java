package com.byron.mytalks;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

public class MyTalksApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}

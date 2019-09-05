package com.clickround.quayomobilitychallenge;

import android.app.Application;
import android.content.Context;

import com.clickround.quayomobilitychallenge.data.local.room.RoomHelper;

import timber.log.Timber;

/**
 * {@link Application} used to initialize Analytics. Code initialized in Application
 * classes is rare since this code will be run any time a ContentProvider, Activity, or Service is
 * used by the user or system. Analytics, dependency injection, and multi-dex frameworks are in this
 * very small set of use cases.
 */
public class ApplicationController extends Application {

    private static ApplicationController mInstance;

    public static synchronized ApplicationController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        RoomHelper.getInstance(getApplicationContext());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}

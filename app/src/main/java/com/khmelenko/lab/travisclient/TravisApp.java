package com.khmelenko.lab.travisclient;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.khmelenko.lab.travisclient.common.Constants;
import com.khmelenko.lab.travisclient.dagger.component.ActivityInjectionComponent;
import com.khmelenko.lab.travisclient.dagger.component.BaseComponent;
import com.khmelenko.lab.travisclient.dagger.component.DaggerActivityInjectionComponent;
import com.khmelenko.lab.travisclient.dagger.component.DaggerBaseComponent;
import com.khmelenko.lab.travisclient.storage.AppSettings;

/**
 * Application class
 *
 * @author Dmytro Khmelenko
 */
public final class TravisApp extends Application {

    private static Context sContext;

    private ActivityInjectionComponent mActivityInjection;

    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

        String server = AppSettings.getServerUrl();
        if (TextUtils.isEmpty(server)) {
            AppSettings.putServerType(0);
            AppSettings.putServerUrl(Constants.OPEN_SOURCE_TRAVIS_URL);
        }

        BaseComponent baseComponent = DaggerBaseComponent.create();

        mActivityInjection = DaggerActivityInjectionComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    /**
     * Gets application context
     *
     * @return Application context
     */
    public static Context getAppContext() {
        return sContext;
    }

    /**
     * Gets application instance
     *
     * @return Application instance
     */
    public static TravisApp instance() {
        return (TravisApp) sContext;
    }

    /**
     * Gets activity injection component
     *
     * @return Activity injection component
     */
    public ActivityInjectionComponent activityInjector() {
        return mActivityInjection;
    }
}

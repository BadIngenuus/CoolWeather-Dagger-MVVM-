package com.android.weatherjecpack.Utils;

import android.app.Application;
import android.content.ContentProvider;
import android.content.Context;
import android.os.Build;

import com.android.weatherjecpack.di.AppComponent;
import com.android.weatherjecpack.di.AppModule;
import com.android.weatherjecpack.di.DaggerAppComponent;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

public class MyApplication extends Application {

    private AppComponent appComponent;
    private static MyApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
        context = this;
    }

    public AppComponent getAppComponent(){return appComponent;}

    public static MyApplication getContext(){return context;}
}

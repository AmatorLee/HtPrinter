package com.amator.htprinter.di.component;

import android.content.Context;

import com.amator.htprinter.di.ContextLife;
import com.amator.htprinter.di.PerApp;
import com.amator.htprinter.di.module.ApplicationModule;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.InitializationConfig;

import dagger.Component;

/**
 * Created by AmatorLee on 2018/4/4.
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ContextLife("Application")
    Context getApplicationContext();

    InitializationConfig getConfig();

    Gson getGson();

}

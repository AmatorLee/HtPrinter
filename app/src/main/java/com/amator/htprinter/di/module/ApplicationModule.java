package com.amator.htprinter.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.di.ContextLife;
import com.amator.htprinter.di.PerApp;
import com.amator.htprinter.module.MyObjectBox;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;

import dagger.Module;
import dagger.Provides;
import io.objectbox.BoxStore;

/**
 * Created by AmatorLee on 2018/4/4.
 */
@Module
public class ApplicationModule {


    private HtPrinterApplcation mApplication;

    public ApplicationModule(HtPrinterApplcation application) {
        mApplication = application;
    }

    @ContextLife("Application")
    @PerApp
    @Provides
    public Context provideApplicationContext(){
        return mApplication.getApplicationContext();
    }

    @Provides
    @PerApp
    public InitializationConfig provideConfig(){
        return InitializationConfig.newBuilder(provideApplicationContext())
                .readTimeout(20000)
                .retry(5)
                .connectionTimeout(30000)
                .networkExecutor(new OkHttpNetworkExecutor())
                .build();
    }


    @Provides
    @PerApp
    public SharedPreferences provideSharePreference(){
        return PreferenceManager.getDefaultSharedPreferences(provideApplicationContext());
    }

    @Provides
    @PerApp
    Gson provideGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @PerApp
    public BoxStore provideBox(){
        return MyObjectBox.builder().androidContext(mApplication.getApplicationContext()).build();
    }



}

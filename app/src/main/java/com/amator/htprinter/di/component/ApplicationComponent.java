package com.amator.htprinter.di.component;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.amator.htprinter.di.ContextLife;
import com.amator.htprinter.di.PerApp;
import com.amator.htprinter.di.module.ApplicationModule;


import dagger.Component;

/**
 * Created by AmatorLee on 2018/4/4.
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ContextLife("Application")
    Context getApplicationContext();


    Handler handler();

    SharedPreferences getSharePreference();
}

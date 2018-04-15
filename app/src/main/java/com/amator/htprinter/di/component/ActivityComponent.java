package com.amator.htprinter.di.component;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.amator.htprinter.di.ContextLife;
import com.amator.htprinter.di.PerActivity;
import com.amator.htprinter.di.module.ActivityModule;
import com.amator.htprinter.ui.activity.BannerContentActivity;
import com.amator.htprinter.ui.activity.HomePageContentActivity;
import com.amator.htprinter.ui.activity.MainActivity;
import com.amator.htprinter.ui.activity.PrinterListActivity;

import dagger.Component;

/**
 * Created by AmatorLee on 2018/4/4.
 */
@PerActivity
@Component(modules = ActivityModule.class, dependencies = ApplicationComponent.class)
public interface ActivityComponent {


    @ContextLife("Application")
    Context getApplicationContext();

    @ContextLife("Activity")
    Context getActivityContext();

    Activity getActivity();

    SharedPreferences getSharePreference();

    void inject(MainActivity mainActivity);

    void inject(BannerContentActivity activity);

    void inject(HomePageContentActivity activity);

    void inject(PrinterListActivity activity);

}

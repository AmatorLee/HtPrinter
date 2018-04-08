package com.amator.htprinter.di.module;

import android.app.Activity;
import android.content.Context;

import com.amator.htprinter.di.ContextLife;
import com.amator.htprinter.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AmatorLee on 2018/4/4.
 */
@Module
public class ActivityModule {

    private Activity mContext;

    public ActivityModule(Activity context) {
        mContext = context;
    }

    @ContextLife("Activity")
    @PerActivity
    @Provides
    public Context provideContext(){
        return mContext;
    }

    @Provides
    @PerActivity
    public Activity provideActivity(){
        return mContext;
    }
}

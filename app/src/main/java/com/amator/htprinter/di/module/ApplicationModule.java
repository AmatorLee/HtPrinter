package com.amator.htprinter.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.di.ContextLife;
import com.amator.htprinter.di.PerApp;


import dagger.Module;
import dagger.Provides;

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
    public SharedPreferences provideSharePreference(){
        return PreferenceManager.getDefaultSharedPreferences(provideApplicationContext());
    }


//    @PerApp
//    @Provides
//    public DaoSession provideDaoSession(){
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mApplication.getApplicationContext(), HtPrinterApplcation.DB_NAME, null);
//        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
//        DaoMaster daoMaster = new DaoMaster(writableDatabase);
//        return daoMaster.newSession();
//    }

    @PerApp
    @Provides
    Handler provideMainHandler(){
        return new Handler(Looper.getMainLooper());
    }


}

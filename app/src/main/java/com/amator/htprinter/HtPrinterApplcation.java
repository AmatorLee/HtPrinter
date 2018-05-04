package com.amator.htprinter;

import android.app.Application;

import com.amator.htprinter.base.Constans;
import com.amator.htprinter.di.component.ApplicationComponent;
import com.amator.htprinter.di.component.DaggerApplicationComponent;
import com.amator.htprinter.di.module.ApplicationModule;
import com.itheima.retrofitutils.ItheimaHttp;

/**
 * Created by AmatorLee on 2018/4/4.
 */

public class HtPrinterApplcation extends Application{

    private static ApplicationComponent sApplicationComponent;
    public static final String DB_NAME = "HtPrinter-db";

    @Override
    public void onCreate() {
        super.onCreate();
//        RxTool.init(this);
        initApplicationComponent();
        ItheimaHttp.init(this, Constans.BASE_URL);
    }

    private void initApplicationComponent() {
        if (sApplicationComponent == null){
            sApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(HtPrinterApplcation.this))
                    .build();
        }
    }

    public synchronized static ApplicationComponent getsApplicationComponent(){
        return sApplicationComponent;
    }
}

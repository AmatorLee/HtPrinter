package com.amator.htprinter;

import android.app.Application;

import com.amator.htprinter.di.component.ApplicationComponent;
import com.amator.htprinter.di.component.DaggerApplicationComponent;
import com.amator.htprinter.di.module.ApplicationModule;
import com.vondear.rxtools.RxTool;
import com.yanzhenjie.nohttp.NoHttp;

/**
 * Created by AmatorLee on 2018/4/4.
 */

public class HtPrinterApplcation extends Application{

    private static ApplicationComponent sApplicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);
        initApplicationComponent();
        NoHttp.initialize(sApplicationComponent.getConfig());
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

package com.amator.htprinter;

import android.app.Application;

import com.amator.htprinter.di.component.ApplicationComponent;
import com.amator.htprinter.di.component.DaggerApplicationComponent;
import com.amator.htprinter.di.module.ApplicationModule;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.vondear.rxtools.RxTool;
import com.yanzhenjie.nohttp.NoHttp;

/**
 * Created by AmatorLee on 2018/4/4.
 */

public class HtPrinterApplcation extends Application{

    private static ApplicationComponent sApplicationComponent;
    public static String BASE_API = "http://112.124.22.238:8081/appstore/";

    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.init(this);
        initApplicationComponent();
        NoHttp.initialize(sApplicationComponent.getConfig());
        ZXingLibrary.initDisplayOpinion(this);
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

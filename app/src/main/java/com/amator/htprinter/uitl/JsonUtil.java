package com.amator.htprinter.uitl;

import com.amator.htprinter.HtPrinterApplcation;

import java.lang.reflect.Type;

import static com.amator.htprinter.HtPrinterApplcation.getsApplicationComponent;

/**
 * Created by AmatorLee on 2018/4/7.
 */

public class JsonUtil {

    public static void toJson(Object t){
        getsApplicationComponent().getGson()
                .toJson(t);
    }

    public static void toJson(Object t,Type type){
        getsApplicationComponent().getGson()
                .toJson(t,type);
    }

    public static Object fromJson(String json,Type type){
        return HtPrinterApplcation.getsApplicationComponent().getGson()
                .fromJson(json,type);
    }

}

package com.amator.htprinter.uitl;

import com.amator.htprinter.HtPrinterApplcation;

/**
 * Created by AmatorLee on 2018/4/12.
 */

public class NetUtils {

    public static boolean isConnect(){
        return RxNetTool.isNetworkAvailable(HtPrinterApplcation.getsApplicationComponent().getApplicationContext());
    }

}

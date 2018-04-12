package com.amator.htprinter.uitl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.amator.htprinter.HtPrinterApplcation;

/**
 * Created by AmatorLee on 2018/4/11.
 */

public class ViewUtil {

    public static Context getContext() {
        return HtPrinterApplcation.getsApplicationComponent().getApplicationContext();
    }

    public static View createView(int viewid) {
        return LayoutInflater.from(getContext())
                .inflate(viewid, null);
    }

    public static <T extends View> T bindView(View parent, int viewid) {
        return (T) parent.findViewById(viewid);
    }

    public static void runInUIThread(Runnable r) {
        HtPrinterApplcation.getsApplicationComponent()
                .handler()
                .post(r);
    }

    public static String getString(int id) {
        String res = getContext().getResources().getString(id);
        return res;
    }

}

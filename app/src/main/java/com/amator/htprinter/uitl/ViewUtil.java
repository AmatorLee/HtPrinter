package com.amator.htprinter.uitl;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;

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

    public static int getColor(int id) {
        return ViewUtil.getContext().getResources().getColor(id);
    }

    public static Drawable getDrawable(int id) {
        Drawable drawable = null;
        drawable = ViewUtil.getContext().getResources().getDrawable(id);
        return drawable;
    }

}

package com.amator.htprinter.uitl;

import android.content.SharedPreferences;

import com.amator.htprinter.HtPrinterApplcation;
import com.amator.htprinter.base.Constans;

/**
 * Created by AmatorLee on 2018/4/17.
 */

public class TimeUtil {

    //设置超时时延
    private static final long OVER_TIME = 3 * 24 * 60 * 60;

    /***
     * 如果第一次缓存或缓存超时则进行缓存
     * @return
     */
    public static boolean isBannerCanCache() {
        long curTime = System.currentTimeMillis();
        SharedPreferences sharePreference = HtPrinterApplcation.getsApplicationComponent().getSharePreference();
        long lastTime = sharePreference.getLong(Constans.BANNER_LASTTIME_KEY, 0L);
        if (lastTime == 0L) {
            return true;
        }
        if (curTime < lastTime) return false;
        long tmp = curTime - lastTime;
        return tmp > OVER_TIME;
    }

    public static boolean isBannerCanQuery(){
        long curTime = System.currentTimeMillis();
        SharedPreferences sharePreference = HtPrinterApplcation.getsApplicationComponent().getSharePreference();
        long lastTime = sharePreference.getLong(Constans.BANNER_LASTTIME_KEY, 0L);
        if (lastTime == 0L) {
            return false;
        }
        long tmp = curTime - lastTime;
        return tmp < OVER_TIME;
    }

    public static boolean isHomePageCanQuery(){
        long curTime = System.currentTimeMillis();
        SharedPreferences sharePreference = HtPrinterApplcation.getsApplicationComponent().getSharePreference();
        long lastTime = sharePreference.getLong(Constans.HOMEPAGE_LASTTIME_KEY, 0L);
        if (lastTime == 0L) {
            return false;
        }
        long tmp = curTime - lastTime;
        return tmp < OVER_TIME;
    }


    /***
     * 如果第一次缓存或缓存超时则进行缓存
     * @return
     */
    public static boolean isHomePageCanCache() {
        long curTime = System.currentTimeMillis();
        SharedPreferences sharePreference = HtPrinterApplcation.getsApplicationComponent().getSharePreference();
        long lastTime = sharePreference.getLong(Constans.HOMEPAGE_LASTTIME_KEY, 0L);
        if (lastTime == 0L) {
            return true;
        }
        if (curTime < lastTime) return false;
        long tmp = curTime - lastTime;
        return tmp > OVER_TIME;
    }

    public static void saveHomePageCurTime() {
        SharedPreferences preference = HtPrinterApplcation.getsApplicationComponent().getSharePreference();
        long lastTime = System.currentTimeMillis();
        preference.edit().putLong(Constans.HOMEPAGE_LASTTIME_KEY, lastTime).apply();
    }

    public static void deleteHomePageCurTimeCache() {
        SharedPreferences preference = HtPrinterApplcation.getsApplicationComponent().getSharePreference();
        long lastTime = 0L;
        preference.edit().putLong(Constans.HOMEPAGE_LASTTIME_KEY, lastTime).apply();
    }

    public static void saveBannerCurTime() {
        SharedPreferences preference = HtPrinterApplcation.getsApplicationComponent().getSharePreference();
        long lastTime = System.currentTimeMillis();
        preference.edit().putLong(Constans.BANNER_LASTTIME_KEY, lastTime).apply();
    }

    public static void deleteBannerCurTimeCache() {
        SharedPreferences preference = HtPrinterApplcation.getsApplicationComponent().getSharePreference();
        long lastTime = 0L;
        preference.edit().putLong(Constans.BANNER_LASTTIME_KEY, lastTime).apply();
    }

}

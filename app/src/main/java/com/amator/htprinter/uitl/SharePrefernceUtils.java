package com.amator.htprinter.uitl;

import android.content.SharedPreferences;

import com.amator.htprinter.HtPrinterApplcation;

/**
 * Created by AmatorLee on 2018/4/26.
 */
public class SharePrefernceUtils {

    private static volatile SharePrefernceUtils sInstance = new SharePrefernceUtils();

    private SharedPreferences mSharedPreferences;

    private SharePrefernceUtils() {
        mSharedPreferences = HtPrinterApplcation.getsApplicationComponent().getSharePreference();
    }

    public synchronized static SharePrefernceUtils getInstance() {
        return sInstance;
    }

    public boolean put(String key, Object value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else {
            return false;
        }
        return true;
    }


    public Object get(String key, Object defValue) {
        if (defValue instanceof String) {
            return mSharedPreferences.getString(key, (String) defValue);
        } else if (defValue instanceof Boolean) {
            return mSharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Long) {
            return mSharedPreferences.getLong(key, (Long) defValue);
        } else if (defValue instanceof Integer) {
            return mSharedPreferences.getInt(key, (Integer) defValue);
        } else {
            return null;
        }
    }


    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

}

package com.amator.htprinter.uitl;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RxLogTool {
    private static final SimpleDateFormat LOG_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat FILE_SUFFIX = new SimpleDateFormat("yyyy-MM-dd");
    private static Boolean LOG_SWITCH = Boolean.valueOf(true);
    private static Boolean LOG_TO_FILE = Boolean.valueOf(false);
    private static String LOG_TAG = "TAG";
    private static char LOG_TYPE = 118;
    private static int LOG_SAVE_DAYS = 7;
    private static String LOG_FILE_PATH;
    private static String LOG_FILE_NAME;

    public RxLogTool() {
    }

    public static void init(Context context) {
        LOG_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + context.getPackageName();
        LOG_FILE_NAME = "Log";
    }

    public static void w(Object msg) {
        w(LOG_TAG, msg);
    }

    public static void w(String tag, Object msg) {
        w(tag, msg, (Throwable)null);
    }

    public static void w(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'w');
    }

    public static void e(Object msg) {
        e(LOG_TAG, msg);
    }

    public static void e(String tag, Object msg) {
        e(tag, msg, (Throwable)null);
    }

    public static void e(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'e');
    }

    public static void d(Object msg) {
        d(LOG_TAG, msg);
    }

    public static void d(String tag, Object msg) {
        d(tag, msg, (Throwable)null);
    }

    public static void d(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'd');
    }

    public static void i(Object msg) {
        i(LOG_TAG, msg);
    }

    public static void i(String tag, Object msg) {
        i(tag, msg, (Throwable)null);
    }

    public static void i(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'i');
    }

    public static void v(Object msg) {
        v(LOG_TAG, msg);
    }

    public static void v(String tag, Object msg) {
        v(tag, msg, (Throwable)null);
    }

    public static void v(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'v');
    }

    private static void log(String tag, String msg, Throwable tr, char level) {
        if(LOG_SWITCH.booleanValue()) {
            if(101 != level || 101 != LOG_TYPE && 118 != LOG_TYPE) {
                if(119 != level || 119 != LOG_TYPE && 118 != LOG_TYPE) {
                    if(100 == level && (100 == LOG_TYPE || 118 == LOG_TYPE)) {
                        Log.d(tag, msg, tr);
                    } else if(105 != level || 100 != LOG_TYPE && 118 != LOG_TYPE) {
                        Log.v(tag, msg, tr);
                    } else {
                        Log.i(tag, msg, tr);
                    }
                } else {
                    Log.w(tag, msg, tr);
                }
            } else {
                Log.e(tag, msg, tr);
            }

            if(LOG_TO_FILE.booleanValue()) {
                log2File(String.valueOf(level), tag, msg + tr == null?"":"\n" + Log.getStackTraceString(tr));
            }
        }

    }

    private static synchronized void log2File(String mylogtype, String tag, String text) {
        Date nowtime = new Date();
        String date = FILE_SUFFIX.format(nowtime);
        String dateLogContent = LOG_FORMAT.format(nowtime) + ":" + mylogtype + ":" + tag + ":" + text;
        File destDir = new File(LOG_FILE_PATH);
        if(!destDir.exists()) {
            destDir.mkdirs();
        }

        File file = new File(LOG_FILE_PATH, LOG_FILE_NAME + date);

        try {
            FileWriter e = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(e);
            bufWriter.write(dateLogContent);
            bufWriter.newLine();
            bufWriter.close();
            e.close();
        } catch (IOException var10) {
            var10.printStackTrace();
        }

    }

    public static void delFile() {
        String needDelFiel = FILE_SUFFIX.format(getDateBefore());
        File file = new File(LOG_FILE_PATH, needDelFiel + LOG_FILE_NAME);
        if(file.exists()) {
            file.delete();
        }

    }

    private static Date getDateBefore() {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(5, now.get(5) - LOG_SAVE_DAYS);
        return now.getTime();
    }
}

package com.amator.htprinter.uitl;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;


import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class RxActivityTool {
    private static Stack<Activity> activityStack;

    public RxActivityTool() {
    }

    public static void addActivity(Activity activity) {
        if(activityStack == null) {
            activityStack = new Stack();
        }

        activityStack.add(activity);
    }

    public static Activity currentActivity() {
        Activity activity = (Activity)activityStack.lastElement();
        return activity;
    }

    public static void finishActivity() {
        Activity activity = (Activity)activityStack.lastElement();
    }

    public static void finishActivity(Activity activity) {
        if(activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }

    }

    public static void finishActivity(Class<?> cls) {
        Iterator var1 = activityStack.iterator();

        while(var1.hasNext()) {
            Activity activity = (Activity)var1.next();
            if(activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }

    }

    public static void finishAllActivity() {
        int size = activityStack.size();

        for(int i = 0; i < size; ++i) {
            if(null != activityStack.get(i)) {
                ((Activity)activityStack.get(i)).finish();
            }
        }

        activityStack.clear();
    }

    public static void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception var2) {
            ;
        }

    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    public static boolean isExistActivity(Context context, String packageName, String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return context.getPackageManager().resolveActivity(intent, 0) != null && intent.resolveActivity(context.getPackageManager()) != null && context.getPackageManager().queryIntentActivities(intent, 0).size() != 0;
    }


    public static void skipActivityAndFinishAll(Context context, Class<?> goal, Bundle bundle) {
        Intent intent = new Intent(context, goal);
        intent.putExtras(bundle);
        intent.setFlags(268468224);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    public static void skipActivityAndFinishAll(Context context, Class<?> goal) {
        Intent intent = new Intent(context, goal);
        intent.setFlags(268468224);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    public static void skipActivityAndFinish(Context context, Class<?> goal, Bundle bundle) {
        Intent intent = new Intent(context, goal);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    public static void skipActivityAndFinish(Context context, Class<?> goal) {
        Intent intent = new Intent(context, goal);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    public static void skipActivity(Context context, Class<?> goal) {
        Intent intent = new Intent(context, goal);
        context.startActivity(intent);
    }

    public static void skipActivity(Context context, Class<?> goal, Bundle bundle) {
        Intent intent = new Intent(context, goal);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void skipActivityForResult(Activity context, Class<?> goal, int requestCode) {
        Intent intent = new Intent(context, goal);
        context.startActivityForResult(intent, requestCode);
    }

    public static void skipActivityForResult(Activity context, Class<?> goal, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, goal);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
    }

    public static String getLauncherActivity(Context context, String packageName) {
        Intent intent = new Intent("android.intent.action.MAIN", (Uri)null);
        intent.addCategory("android.intent.category.LAUNCHER");
        PackageManager pm = context.getPackageManager();
        List infos = pm.queryIntentActivities(intent, 0);
        Iterator var5 = infos.iterator();

        ResolveInfo info;
        do {
            if(!var5.hasNext()) {
                return "no " + packageName;
            }

            info = (ResolveInfo)var5.next();
        } while(!info.activityInfo.packageName.equals(packageName));

        return info.activityInfo.name;
    }
}

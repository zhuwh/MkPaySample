package com.mark.app.mkpay.strip.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.util.Log;


/**
 * AndroidManifest读取Meta-data工具类
 * Created by lenovo on 2017/12/19.
 */

public class MetaDataUtil {
    private static final String TAG = "MetaDataUtil";

    /**
     * 获取Application标签下的meta-data
     *
     * @param context
     * @param key
     * @return
     */
    public static <T> T getApplicationInfo(Context context, String key) {
        ApplicationInfo info = null;
        String log = null;
        try {
            String packageName = context.getPackageName();
            info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            log = e.getMessage();
            e.printStackTrace();
        }
        if (info == null) {
            logI(TAG, "getApplicationInfo", "ApplicationInfo获取失败>>>" + "key:" + key + "---" + log);
            return null;
        }
        return (T) info.metaData.get(key);
    }

    /**
     * 获取Activity标签下的meta-data
     *
     * @param activity
     * @param key
     * @return
     */
    public static String getActivityInfo(Activity activity, String key) {
        ActivityInfo info = null;
        String log = null;
        try {
            ComponentName componentName = activity.getComponentName();
            info = activity.getPackageManager().getActivityInfo(componentName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            log = e.getMessage();
            e.printStackTrace();
        }
        if (info == null) {
            logI(TAG, "getActivityInfo", "ActivityInfo获取失败>>>" + log);
            return "";
        }
        return info.metaData.getString(key);
    }

    /**
     * 获取service标签下的meta-data
     *
     * @param activity
     * @param key
     * @return
     */
    public static String getServiceInfo(Activity activity, String key) {
        ServiceInfo info = null;
        String log = null;
        try {
            ComponentName componentName = activity.getComponentName();
            info = activity.getPackageManager().getServiceInfo(componentName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            log = e.getMessage();
            e.printStackTrace();
        }
        if (info == null) {
            logI(TAG, "getServiceInfo", "ServiceInfo获取失败>>>" + log);
            return "";
        }
        return info.metaData.getString(key);
    }

    /**
     * 获取receiver标签下的meta-data
     *
     * @param activity
     * @param key
     * @return
     */
    public static String getReceiverInfo(Activity activity, String key) {
        ActivityInfo info = null;
        String log = null;
        try {
            ComponentName componentName = activity.getComponentName();
            info = activity.getPackageManager().getReceiverInfo(componentName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            log = e.getMessage();
            e.printStackTrace();
        }
        if (info == null) {
            logI(TAG, "getReceiverInfo", "ActivityInfo获取失败>>>" + log);
            return "";
        }
        return info.metaData.getString(key);
    }

    private static void logI(String tag, String method, String desc) {
        String result = "[className]" + tag + "---[method]" + method + "---[desc]" + desc;
        Log.i("LogUtil", result);
    }
}

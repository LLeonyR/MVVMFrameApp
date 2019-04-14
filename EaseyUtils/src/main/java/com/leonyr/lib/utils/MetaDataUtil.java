package com.leonyr.lib.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * ==============================================================
 * Description: 获取meta data
 * meta data字段获取
 * <p>
 * Created by leonyr on 2019.04.09
 * (C) Copyright LeonyR Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
public class MetaDataUtil {

    public static String getMetaDataUmenKey(Context c){
        return getMetaDataFromApp(c, "UMENG_APPKEY");
    }

    public static String getMetaDataUmenChannel(Context c){
        return getMetaDataFromApp(c, "UMENG_CHANNEL");
    }

    public static String getMetaDataFromApp(Context c, String key) {
        String value = "";
        try {
            ApplicationInfo appInfo = c.getPackageManager().getApplicationInfo(c.getPackageName(),
                    PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

}

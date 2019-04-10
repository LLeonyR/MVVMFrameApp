package com.leonyr.lib.utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * ==============================================================
 * Description:
 * <p>
 * Created by leonyr on 2019.04.10
 * (C) Copyright leonyr Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
public class AssetsUtil {
    private AssetsUtil() {

    }

    /**
     * 根据文件路径获取assets目录文件
     *
     * @param context
     * @param fileName
     * @return 返回String
     */
    public static String getStringByAssets(Context context, String fileName) {
        String resultString = "";
        InputStream inputStream = null;
        try {
            inputStream = context.getClass().getClassLoader().getResourceAsStream("assets/" + fileName);
            byte[] buffer = new byte[inputStream.available()];
            int readLength = inputStream.read(buffer);
            if (readLength > 0) {
                resultString = new String(buffer, "UTF-8");
            }
            return resultString;
        } catch (Exception var5) {
            LogUtil.e(LogUtil.DEFAULT_TAG, "getStringByAssets error =" + var5);
            return resultString;
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

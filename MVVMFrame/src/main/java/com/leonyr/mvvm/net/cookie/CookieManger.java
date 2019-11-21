package com.leonyr.mvvm.net.cookie;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * ==============================================================
 * Description:
 * <p>
 * Created by 01385127 on 2019.04.29
 * (C) Copyright sf_Express Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
public class CookieManger implements CookieJar {

    private static final String TAG = "CookieManger";
    private static Context mContext;
    private static CookieStore cookieStore;

    /**
     * Mandatory constructor for the CookieManger
     */
    public CookieManger(Context context) {
        mContext = context;
        if (cookieStore == null) {
            cookieStore = new CookieStore(mContext);
        }
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }

}
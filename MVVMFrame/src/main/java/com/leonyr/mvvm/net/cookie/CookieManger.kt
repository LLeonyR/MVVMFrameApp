package com.leonyr.mvvm.net.cookie

import android.content.Context
import com.leonyr.mvvm.net.cookie.CookieManger.Companion.cookieStore

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * ==============================================================
 * Description:
 *
 *
 * Created by 01385127 on 2019.04.29
 * (C) Copyright sf_Express Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
class CookieManger
/**
 * Mandatory constructor for the CookieManger
 */
    (context: Context) : CookieJar {

    init {
        mContext = context
        if (cookieStore == null) {
            cookieStore = CookieStore(mContext)
        }
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (cookies != null && cookies.size > 0) {
            for (item in cookies) {
                cookieStore!!.add(url, item)
            }
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore!!.get(url)
    }

    companion object {

        private val TAG = "CookieManger"
        private lateinit var mContext: Context
        private var cookieStore: CookieStore? = null
    }

}
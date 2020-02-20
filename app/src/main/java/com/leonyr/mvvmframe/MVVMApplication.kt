package com.leonyr.mvvmframe

import android.app.Application
import android.content.Context
import com.leonyr.lib.Utils

import com.leonyr.lib.utils.ToastUtil
import com.leonyr.mvvm.BaseApplication
import com.leonyr.mvvm.net.NetClient

/**
 * ==============================================================
 * Description:
 *
 *
 * Created by 01385127 on 2019.04.28
 * (C) Copyright sf_Express Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
class MVVMApplication : BaseApplication() {

    companion object {
        lateinit var apiService: GitHubApi
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        apiService = NetClient.Builder()
                .setDebug(true)
                .build("http://47.111.0.167:8888/", GitHubApi::class.java)
    }

    override fun onCreate() {
        super.onCreate()

    }
}

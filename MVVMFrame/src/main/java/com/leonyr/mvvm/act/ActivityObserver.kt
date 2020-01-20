package com.leonyr.mvvm.act

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent

import com.leonyr.lib.utils.LogUtil

/**
 * ==============================================================
 * Description: 生命周期监听
 *
 *
 * Created by leonyr on 2019-04-20
 * (C) Copyright LeonyR Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
class ActivityObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        LogUtil.w("Activity onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        LogUtil.w("Activity onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        LogUtil.w("Activity onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        LogUtil.w("Activity onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        LogUtil.w("Activity onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        LogUtil.w("Activity onDestroy")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny() {
        LogUtil.w("Activity onAny")
    }

}

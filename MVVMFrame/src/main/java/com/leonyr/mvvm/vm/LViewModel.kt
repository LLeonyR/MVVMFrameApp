package com.leonyr.mvvm.vm

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext


/**
 * ==============================================================
 * Description:context aware [ViewModel].
 *
 *
 * Created by 01385127 on 2019.04.28
 * (C) Copyright sf_Express Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
open class LViewModel(ctx: Context) : ViewModel() {

    //加载框显示
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()

    @SuppressLint("StaticFieldLeak")
    private val context: WeakReference<Context> = WeakReference(ctx)

    private val parentJob = SupervisorJob()

    private val uiCoroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val ioCoroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO

    protected val uiScope = CoroutineScope(uiCoroutineContext)
    protected val ioScope = CoroutineScope(ioCoroutineContext)

    /**
     * Return the application.
     */
    fun getContext(): Context? {
        return context.get()
    }

    companion object {

        /**
         * 简化创建ViewModel
         *
         * @param a      创建的activity
         * @param tClass ViewModel类别
         * @returna
         */
        fun <C : ViewModel> create(a: FragmentActivity, tClass: Class<C>): C {
            return ViewModelProviders.of(a, LViewModelFactory(a)).get(tClass)
        }

        /**
         * 简化创建ViewModel
         *
         * @param f      创建的activity
         * @param tClass ViewModel类别
         * @returna
         */
        fun <C : ViewModel> create(f: Fragment, tClass: Class<C>): C {
            return ViewModelProviders.of(f, LViewModelFactory(f.getContext())).get(tClass)
        }

        /**
         * 简化创建ViewModel
         *
         * @param a      创建的activity
         * @param tClass ViewModel类别
         * @returna
         */
        fun <C : ViewModel> createAndroid(a: FragmentActivity, tClass: Class<C>): C {
            return ViewModelProviders.of(
                a,
                ViewModelProvider.AndroidViewModelFactory(a.application)
            ).get(tClass)
        }

        /**
         * 简化创建ViewModel
         *
         * @param f      创建的activity
         * @param tClass ViewModel类别
         * @returna
         */
        fun <C : ViewModel> createAndroid(f: Fragment, tClass: Class<C>): C {
            return ViewModelProviders.of(
                f,
                ViewModelProvider.AndroidViewModelFactory(f.getActivity()!!.getApplication())
            ).get(tClass)
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onCleared() {
        super.onCleared()

        uiScope.coroutineContext.cancelChildren(null)
        ioScope.coroutineContext.cancelChildren(null)
    }

}

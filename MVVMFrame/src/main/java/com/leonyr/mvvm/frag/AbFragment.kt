package com.leonyr.mvvm.frag

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.Nullable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.leonyr.mvvm.vm.LViewModel

/**
 * ==============================================================
 * Description: fragment 基类
 *
 *
 * Created by leonyr on 2019-04-20.04.20
 * (C) Copyright LeonyR Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
abstract class AbFragment<VM : LViewModel> : IFragment() {

    lateinit var TAG: String
    var vModel: VM? = null
        get() {
            if (field == null) {
                throw NullPointerException("You should setViewModel first!")
            }
            return field
        }
    protected var mCtx: Context? = null
    var rootView: View? = null
        protected set

    @get:LayoutRes
    protected abstract val layoutResId: Int

    init {
        lifecycle.addObserver(FragmentObserver())
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mCtx = context
        TAG = javaClass.simpleName
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (rootView == null) {
            rootView = inflater.inflate(layoutResId, container)
            initView(rootView, savedInstanceState)
        }
        val parent = rootView!!.parent as ViewGroup
        parent.removeView(rootView)
        return rootView
    }

    protected abstract fun initView(rootView: View?, savedInstanceState: Bundle?)
}

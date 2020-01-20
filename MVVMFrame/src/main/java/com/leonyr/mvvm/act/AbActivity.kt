package com.leonyr.mvvm.act

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window


import com.leonyr.mvvm.vm.LViewModel

/**
 * ==============================================================
 * Description: activity 基类
 *
 *
 * Created by leonyr on 2019-04-20.04.20
 * (C) Copyright LeonyR Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
abstract class AbActivity<VM : LViewModel> : AppCompatActivity() {

    lateinit var TAG: String
    lateinit var mCtx: Context
    lateinit var rootView: View
        private set

    var vModel: VM? = null
        get() {
            if (field == null) {
                throw NullPointerException("You should setViewModel first!")
            }
            return field
        }

    /**
     * 获取layout id
     */
    @get:LayoutRes
    protected abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        mCtx = this
        lifecycle.addObserver(ActivityObserver())

        setContentView(layoutResId)
        TAG = javaClass.simpleName

        initView()
    }

    override fun setContentView(layoutResID: Int) {
        rootView = View.inflate(this, layoutResID, null)
        super.setContentView(rootView)
    }

    override fun onResume() {
        super.onResume()
        TAG = javaClass.simpleName
    }

    /**
     * 初始化各类view对象
     */
    protected abstract fun initView()

}

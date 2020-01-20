package com.leonyr.mvvm.act

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.Window

import com.leonyr.mvvm.vm.LViewModel

/**
 * ==============================================================
 * Description: databing activity 基类
 *
 *
 * Created by leonyr on 2019-04-20
 * (C) Copyright LeonyR Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
abstract class AbBindActivity<VM : LViewModel, T : ViewDataBinding> : AppCompatActivity() {

    lateinit var TAG: String
    lateinit var mCtx: Context
    lateinit var binding: T

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
        lifecycle.addObserver(ActivityObserver())

        mCtx = this
        binding = DataBindingUtil.setContentView(this, layoutResId)
        TAG = javaClass.simpleName

        initView(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        TAG = javaClass.simpleName
    }

    /**
     * 初始化其他数据Data
     */
    protected abstract fun initView(savedInstanceState: Bundle?)

}

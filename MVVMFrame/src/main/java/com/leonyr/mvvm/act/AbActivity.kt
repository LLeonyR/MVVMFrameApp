package com.leonyr.mvvm.act

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import com.leonyr.mvvm.net.NetLoading


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

    lateinit var dialogLoading: NetLoading

    lateinit var TAG: String
    lateinit var mCtx: Context
    lateinit var rootView: View
        private set

    lateinit var vModel: VM

    /**
     * 获取layout id
     */
    @get:LayoutRes
    protected abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        mCtx = this
        dialogLoading = NetLoading(mCtx!!)
        lifecycle.addObserver(ActivityObserver())

        setContentView(layoutResId)
        TAG = javaClass.simpleName

        initView()
    }

    fun observerLoading() {
        vModel.showLoading.observe(this, Observer { show ->
            if (show) {
                dialogLoading.show()
            } else {
                dialogLoading.dismiss()
            }
        })
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

    /*override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }*/
}

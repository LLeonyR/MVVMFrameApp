package com.leonyr.mvvm.act

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import com.leonyr.mvvm.net.NetLoading

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

    lateinit var dialogLoading: NetLoading

    lateinit var TAG: String
    lateinit var mCtx: Context
    lateinit var binding: T

    lateinit var vModel: VM

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
        dialogLoading = NetLoading(mCtx)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        TAG = javaClass.simpleName

        initView(savedInstanceState)
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

    override fun onResume() {
        super.onResume()
        TAG = javaClass.simpleName

    }

    /**
     * 初始化其他数据Data
     */
    protected abstract fun initView(savedInstanceState: Bundle?)

    /*override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }*/

}

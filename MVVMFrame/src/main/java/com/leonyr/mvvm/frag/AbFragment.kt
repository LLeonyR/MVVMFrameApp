package com.leonyr.mvvm.frag

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import com.leonyr.mvvm.net.NetLoading

import com.leonyr.mvvm.vm.LViewModel

//import pub.devrel.easypermissions.EasyPermissions

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

    lateinit var dialogLoading: NetLoading

    lateinit var TAG: String
    lateinit var vModel: VM
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
        dialogLoading = NetLoading(mCtx!!)
        TAG = javaClass.simpleName
    }

    @Nullable override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (rootView == null) {
            rootView = inflater.inflate(layoutResId, container)
            initView(rootView, savedInstanceState)
        }
        val parent = rootView!!.parent as ViewGroup
        parent.removeView(rootView)
        return rootView
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

    protected abstract fun initView(rootView: View?, savedInstanceState: Bundle?)

    /*override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }*/
}

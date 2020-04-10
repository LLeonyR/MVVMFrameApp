package com.leonyr.mvvm.frag

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
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
abstract class AbBindFragment<VM : LViewModel, B : ViewDataBinding> : IFragment() {

    lateinit var dialogLoading: NetLoading

    lateinit var TAG: String
    lateinit var vModel: VM
    lateinit var mCtx: Context
    lateinit var binding: B

    @get:LayoutRes
    protected abstract val layoutResId: Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mCtx = context
        dialogLoading = NetLoading(mCtx)
        TAG = javaClass.simpleName
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

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        initView(binding.root, savedInstanceState)

        if(binding.root.parent != null){
            val parent = binding.root.parent as ViewGroup
            parent.removeView(binding.root)
        }

        return binding.root
    }

    protected abstract fun initView(rootView: View, savedInstanceState: Bundle?)

    /*override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }*/
}

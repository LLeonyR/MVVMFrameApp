package com.leonyr.mvvm.frag

import android.app.Dialog
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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.leonyr.lib.utils.LogUtil
import com.leonyr.mvvm.R
import com.leonyr.mvvm.net.NetLoading
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
abstract class AbBindBottomDialogFragment<VM : LViewModel, B : ViewDataBinding> :
    BottomSheetDialogFragment() {

    lateinit var dialogLoading: NetLoading
    lateinit var TAG: String
    lateinit var vModel: VM
    lateinit var mCtx: Context
    lateinit var binding: B

    @get:LayoutRes
    protected abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mCtx = context
        dialogLoading = NetLoading(mCtx)
        TAG = javaClass.simpleName
    }

    fun observerLoading() {
        vModel.showLoading.observe(this, Observer { show ->
            LogUtil.e("${show}}")
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

        if(binding.root.parent != null){
            val parent = binding.root.parent as ViewGroup
            parent.removeView(binding.root)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
    }

    protected abstract fun initView(rootView: View, savedInstanceState: Bundle?)
}

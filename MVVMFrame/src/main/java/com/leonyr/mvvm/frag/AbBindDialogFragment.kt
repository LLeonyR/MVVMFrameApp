package com.leonyr.mvvm.frag

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer

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
abstract class AbBindDialogFragment<VM : LViewModel, B : ViewDataBinding> : DialogFragment() {

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

        dialog.setCanceledOnTouchOutside(false)

        return binding.root
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
        val window = dialog.window
        if (window != null) {
            val dm = DisplayMetrics()
            window.windowManager.defaultDisplay.getMetrics(dm)
            window.setLayout(
                (dm.widthPixels * WIDTH_RATIO).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
        super.onResume()
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.CommonDialogStyle)
    }

    protected abstract fun initView(rootView: View, savedInstanceState: Bundle?)

    companion object {
        protected var WIDTH_RATIO = 0.85f
    }
}

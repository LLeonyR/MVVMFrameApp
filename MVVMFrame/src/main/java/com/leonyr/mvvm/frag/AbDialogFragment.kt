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
import androidx.fragment.app.DialogFragment
import com.leonyr.mvvm.R
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
abstract class AbDialogFragment<VM : LViewModel> : DialogFragment() {

    lateinit var TAG: String
    var vModel: VM? = null
        get() {
            if (field == null) {
                throw NullPointerException("You should setViewModel first!")
            }
            return field
        }
    lateinit var mCtx: Context
    var rootView: View? = null
        protected set

    @get:LayoutRes
    protected abstract val layoutResId: Int

    init {
        lifecycle.addObserver(FragmentObserver())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mCtx = context
        TAG = javaClass.simpleName
    }

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
        dialog.setCanceledOnTouchOutside(false)
        return rootView
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.CommonDialogStyle)
    }

    protected abstract fun initView(rootView: View?, savedInstanceState: Bundle?)

    companion object {
        protected var WIDTH_RATIO = 0.85f
    }
}

package com.leonyr.mvvm.vh

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.ViewGroup

import me.drakeet.multitype.ItemViewBinder

/**
 * ==============================================================
 * Description:
 *
 *
 * Created by leonyr on 2019-04-20.04.20
 * (C) Copyright LeonyR Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
abstract class AbViewBinder<Data, Binding : ViewDataBinding> :
    ItemViewBinder<Data, BindViewHolder<Binding>>() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    @NonNull
    override fun onCreateViewHolder(
        @NonNull inflater: LayoutInflater,
        @NonNull parent: ViewGroup
    ): BindViewHolder<Binding> {
        val binding = DataBindingUtil.inflate<Binding>(inflater, layoutId, parent, false)
        return BindViewHolder(binding)
    }

}

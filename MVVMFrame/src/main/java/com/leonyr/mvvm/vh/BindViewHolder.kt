package com.leonyr.mvvm.vh

import android.databinding.ViewDataBinding
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView

/**
 * ==============================================================
 * Description:
 *
 *
 * Created by leonyr on 2019-04-20.04.20
 * (C) Copyright LeonyR Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
class BindViewHolder<T : ViewDataBinding>(@param:NonNull @field:NonNull val binding: T) : RecyclerView.ViewHolder(binding.root)
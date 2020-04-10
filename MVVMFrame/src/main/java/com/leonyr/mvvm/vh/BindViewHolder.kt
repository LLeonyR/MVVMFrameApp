package com.leonyr.mvvm.vh

import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


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
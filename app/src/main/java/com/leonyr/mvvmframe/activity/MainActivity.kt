package com.leonyr.mvvmframe.activity

import android.widget.TextView
import com.leonyr.mvvm.act.AbActivity
import com.leonyr.mvvm.vm.LViewModel
import com.leonyr.mvvmframe.MVVMApplication
import com.leonyr.mvvmframe.R
import com.leonyr.mvvmframe.UserViewModel

class MainActivity : AbActivity<UserViewModel>() {

    /**
     * 获取layout id
     */
    override val layoutResId: Int
        get() = R.layout.activity_main

    /**
     * 初始化各类view对象
     */
    override fun initView() {

        vModel = LViewModel.create(this, UserViewModel::class.java)

        findViewById<TextView>(R.id.refresh).setOnClickListener{
            vModel?.actionUserLogin()
        }
    }

}

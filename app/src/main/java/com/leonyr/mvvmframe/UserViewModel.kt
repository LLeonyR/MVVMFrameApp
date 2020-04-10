package com.leonyr.mvvmframe

import android.content.Context
import com.leonyr.lib.utils.LogUtil
import com.leonyr.mvvm.vm.LViewModel
import kotlinx.coroutines.launch

class UserViewModel(ctx: Context) : LViewModel(ctx) {

    val userRepository =  UserRepository()

    /*fun actionUserLogin() {
        ioScope.launch {
            val response = userRepository
                    .actionUserLogin()
            response?.let{
                LogUtil.d("message")
            }
        }
    }*/

}
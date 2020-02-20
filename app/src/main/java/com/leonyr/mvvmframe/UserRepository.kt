package com.leonyr.mvvmframe

import com.leonyr.mvvm.net.BaseRepository
import com.squareup.moshi.Moshi
import org.json.JSONObject

class UserRepository : BaseRepository() {

    suspend fun actionUserLogin(): UserModel? {



        val params = JSONObject()
        params.put("mobile", "15657084262")
        params.put("password", "qwerty")

        return safeApiCall(
                call = {
                    MVVMApplication.apiService.login(params)
                },
                errorMessage = "登录失败"
        )
    }
}
package com.leonyr.mvvmframe

import com.google.gson.JsonObject
import com.leonyr.mvvm.net.BaseRepository
import org.json.JSONObject

class UserRepository : BaseRepository() {

    /*suspend fun actionUserLogin(): UserModel? {

        val params = JsonObject()
        params.addProperty("mobile", "15657084262")
        params.addProperty("password", "qwerty")

        return safeApiCall(
                call = {
                    MVVMApplication.apiService.login(params)
                },
                errorMessage = "登录失败"
        )
    }*/
}
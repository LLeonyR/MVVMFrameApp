package com.leonyr.mvvm.net

import com.leonyr.lib.utils.LogUtil
import com.leonyr.mvvm.net.Result.Companion.SUCCESS
import retrofit2.Response
import java.io.IOException

open class BaseRepository{

    /*suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String = ""): T? {
        val result: Result<T> = safeApiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is Result.Success -> data = result.data

            is Result.Error -> {
                LogUtil.e("$errorMessage & exception - ${result.exception}")
            }
        }

        return data
    }*/

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String = ""): Result<T> {
        return safeApiResult(call, errorMessage)
    }

    private suspend fun <T : Any> safeApiResult(
            call: suspend () -> Response<T>,
            errorMessage: String
    ): Result<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                return Result<T>(SUCCESS, response.body() as T)
            } else {
                return Result(response.code(), error = response.message())
                LogUtil.e("isFailure: " + response.message())
            }
        } catch (e: IOException) {
            LogUtil.e(e.message)
            return Result(-1, error = e.message?: "$errorMessage")
        }

        return Result(-1, error = "$errorMessage")
    }
}


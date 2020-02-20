package com.leonyr.mvvm.net

import com.leonyr.lib.utils.LogUtil
import retrofit2.Response
import java.io.IOException

open class BaseRepository{

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {
        val result: Result<T> = safeApiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is Result.Success -> data = result.data

            is Result.Error -> {
                LogUtil.e("$errorMessage & exception - ${result.exception}")
            }
        }

        return data
    }

    private suspend fun <T : Any> safeApiResult(
            call: suspend () -> Response<T>,
            errorMessage: String
    ): Result<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                return Result.Success(response.body()!!)
            } else {
                LogUtil.e("isFailure: " + response.message())
            }
        } catch (e: IOException) {
            LogUtil.e(e.message)
        }

        return Result.Error(IOException("An error occurred while executing , *custom ERROR* - $errorMessage"))
    }
}


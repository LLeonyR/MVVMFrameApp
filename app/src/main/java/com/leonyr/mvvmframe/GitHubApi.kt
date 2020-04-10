package com.leonyr.mvvmframe

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * ==============================================================
 * Description:
 *
 *
 * Created by 01385127 on 2019.04.29
 * (C) Copyright sf_Express Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
interface GitHubApi {

    @POST("api/login")
    suspend fun login(@Body params: JsonObject): Response<UserModel>

}

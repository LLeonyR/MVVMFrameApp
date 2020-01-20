package com.leonyr.mvvm.net

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

abstract class AbInterceptor(val headers: HashMap<String, String>) : Interceptor {

    abstract fun handlerHeaders()

    override fun intercept(chain: Interceptor.Chain): Response {
        var builder = chain.request() as Request.Builder
        headers.forEach {
            builder.addHeader(it.key, it.value)
        }
        return chain.proceed(builder.build())
    }

}
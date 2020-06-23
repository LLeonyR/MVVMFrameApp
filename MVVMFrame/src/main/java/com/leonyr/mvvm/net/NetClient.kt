package com.leonyr.mvvm.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.leonyr.lib.Utils
import com.leonyr.mvvm.BaseApplication
import com.leonyr.mvvm.net.converter.GsonConverterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

class NetClient<T> private constructor() {

    companion object {
        val DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        val CACHE_DIR = "HttpResponseCache"
        val HTTP_RESPONSE_DISK_CACHE_MAX_SIZE: Long = 30 * 1024 * 1024
        var TIME_OUT: Long = 20
        lateinit var httpClient: OkHttpClient
        lateinit var defaultGson: Gson
        lateinit var API_HOST: String
        lateinit var interceptors: ArrayList<Interceptor>

        lateinit var retrofit: Retrofit
    }

    class Builder {

        init {
            interceptors = arrayListOf()
        }

        private var DEBUG = false

        fun setDebug(debug: Boolean): Builder {
            DEBUG = debug
            return this
        }

        private fun createHttpClient(): Builder {
            val httpClientBuilder = OkHttpClient.Builder()

            httpClientBuilder
                    .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT, TimeUnit.SECONDS)

            val baseDir = BaseApplication.App.cacheDir
            val cacheDir = File(baseDir!!, CACHE_DIR)
            httpClientBuilder.cache(Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE))

            if (DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                httpClientBuilder.addInterceptor(interceptor)
            }

            interceptors.forEach {
                httpClientBuilder.addInterceptor(it)
            }

            httpClient = httpClientBuilder.build()

            return this
        }

        private fun createDefaultGson(): Builder {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.setDateFormat(DEFAULT_DATE_FORMAT)
            defaultGson = gsonBuilder.create()
            return this
        }

        fun addInterceptor(interceptor: Interceptor): Builder {
            interceptors.add(interceptor)
            return this
        }

        fun setTimeOut(time: Long): Builder {
            TIME_OUT = time
            return this
        }

        fun <T> build(host: String, clazz: Class<T>): T {
            API_HOST = host
            createDefaultGson()
            createHttpClient()
            retrofit = Retrofit.Builder()
                    .baseUrl(API_HOST)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build()

            return retrofit.create(clazz)
        }

    }

}

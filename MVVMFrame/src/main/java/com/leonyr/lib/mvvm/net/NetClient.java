package com.leonyr.lib.mvvm.net;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leonyr.lib.BuildConfig;
import com.leonyr.lib.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ==============================================================
 * Description:
 * <p>
 * Created by leonyr on 2019-04-28
 * (C) Copyright LeonyR Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
public class NetClient {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String CACHE_DIR = "HttpResponseCache";
    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 30 * 1024 * 1024;
    private static final int TIME_OUT = 20;
    static int timeout = TIME_OUT;
    static Converter.Factory converterFactory;
    @NonNull
    private static OkHttpClient httpClient;
    @NonNull
    private static Gson defaultGson;
    private static String API_HOST;
    private static List<Interceptor> interceptors;

    private NetClient() {

    }

    public static <T> T build(Class<T> tClass) {
        //初始化okHttpClient
        if (null == API_HOST) {
            throw new NullPointerException("Your server url is null!");
        } else if (null == httpClient) {
            httpClient = createOkHttpClient();
        }
        //初始化Gson
        if (null == defaultGson) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat(DEFAULT_DATE_FORMAT);
            defaultGson = gsonBuilder.create();
        }
        return new Retrofit.Builder()
                .addConverterFactory(null == converterFactory ? GsonConverterFactory.create(defaultGson) : converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .baseUrl(API_HOST)
                .build()
                .create(tClass);
    }

    @NonNull
    private static OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //  读写、连接超时时间
        builder.readTimeout(timeout, TimeUnit.SECONDS)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS);

        //  缓存目录
        final File baseDir = Utils.getApp().getCacheDir();
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, CACHE_DIR);
            builder.cache(new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE));
        }

        //  调试模式下，添加日志拦截器
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        //拦截添加
        if (null != interceptors) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        return builder.build();
    }

    public static void setApiHost(String ApiHost) {
        API_HOST = ApiHost;
    }

    public static void setClient(OkHttpClient client) {
        httpClient = client;
    }

    public static void addInterceptors(Interceptor interceptor) {
        if (null == interceptors) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(interceptor);
    }

    public static void setConverterFactory(Converter.Factory factory) {
        converterFactory = factory;
    }

    public static void setTimeout(int time) {
        timeout = time;
    }

    @NonNull
    public Gson getDefaultGson() {
        return defaultGson;
    }

    public static void setDefaultGson(Gson gson) {
        defaultGson = gson;
    }

    @NonNull
    public OkHttpClient getHttpClient() {
        return httpClient;
    }

}

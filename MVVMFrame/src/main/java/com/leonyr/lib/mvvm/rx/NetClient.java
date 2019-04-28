package com.leonyr.lib.mvvm.rx;

import android.support.annotation.NonNull;

import com.leonyr.lib.BuildConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * ==============================================================
 * Description:
 * <p>
 * Created by leonyr on 2019-04-28
 * (C) Copyright LeonyR Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
public class NetClient {

    /**
     * 缓存目录文件夹名称
     */
    private static final String CACHE_DIR = "HttpResponseCache";
    /**
     * 缓存大小（30M）
     */
    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 30 * 1024 * 1024;
    /**
     * 默认的时间格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //测试环境
    public static final String BASE_URL = BuildConfig.API_HOST;

    /**
     * 超时时间
     */
    private static final int TIME_OUT = 20;

    @NonNull
    private final OkHttpClient okHttpClient;
    @NonNull
    private final NetApi apiService;
    @NonNull
    private final Gson defaultGson;

    private NetClient() {
        okHttpClient = createOkHttpClient();
        defaultGson = getGsonBuilder().create();
        apiService = createDefaultApiService(okHttpClient, defaultGson);
    }

    @NonNull
    private NetApi createDefaultApiService(OkHttpClient okHttpClient, Gson gson) {
        return createRetrofitBuilder(okHttpClient, gson)
                .baseUrl(BASE_URL)
                .build()
                .create(NetApi.class);
    }

    @NonNull
    private Retrofit.Builder createRetrofitBuilder(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client);
    }

    @NonNull
    public Gson getDefaultGson() {
        return defaultGson;
    }

    @NonNull
    private GsonBuilder getGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DEFAULT_DATE_FORMAT);
        return gsonBuilder;
    }

    @NonNull
    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //  读写、连接超时时间
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS);

        //  缓存目录
        final File baseDir = CacheUtil.getDiskCacheDir(ContextUtil.getContext());
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, CACHE_DIR);
            builder.cache(new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE));
        }

//        builder.addInterceptor(new EncryptInterceptor());
        //  调试模式下，添加日志拦截器
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        //  BI 平台接口需要将 Token 添加到 Header
        builder.addInterceptor(new NetHeaderInterceptors(InitApplication.getInstance()));
        return builder.build();
    }

    @NonNull
    public static NetApi getApi() {
        return getInstance().apiService;
    }

    private static final class SingletonHolder {
        private static final NetClient INSTANCE = new NetClient();
    }

    @NonNull
    public static NetClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @NonNull
    public NetApi getApiService() {
        return apiService;
    }

    @NonNull
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}

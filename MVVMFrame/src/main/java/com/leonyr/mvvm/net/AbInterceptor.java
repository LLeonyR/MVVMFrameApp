package com.leonyr.mvvm.net;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ==============================================================
 * Description:
 * <p>
 * Created by leonyr on 2019.04.29
 * (C) Copyright LeonyR Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
public abstract class AbInterceptor implements Interceptor {
    protected Map<String, String> headers;

    public AbInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    abstract void handlerHeaders();

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request()
                .newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }
        return chain.proceed(builder.build());

    }
}
package com.leonyr.lib.mvvm.vm;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * ==============================================================
 * Description:context aware {@link ViewModel}.
 * <p>
 * Created by 01385127 on 2019.04.28
 * (C) Copyright sf_Express Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
public class LViewModel extends ViewModel{

    @SuppressLint("StaticFieldLeak")
    private Context context;

    public LViewModel(@NonNull Context ctx) {
        context = ctx;
    }

    /**
     * Return the application.
     */
    @SuppressWarnings("TypeParameterUnusedInFormals")
    @NonNull
    public <T extends Context> T getContext() {
        //noinspection unchecked
        return (T) context;
    }

}

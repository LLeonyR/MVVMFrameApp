package com.leonyr.lib.mvvm.vm;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * ==============================================================
 * Description: viewmodel 基类
 * <p>
 * Created by leonyr on 2019-04-20.04.20
 * (C) Copyright LeonyR Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
public class ViewModel extends android.arch.lifecycle.ViewModel {

    private WeakReference<Context> mCtx;

    public ViewModel(Context ctx){
        mCtx = new WeakReference<>(ctx);
    }

    protected Context getContext(){

        if (null == mCtx || null == mCtx.get()){
            throw new NullPointerException("You must set constructor at first !");
        }
        return mCtx.get();
    }
}

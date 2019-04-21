package com.leonyr.lib.mvvm.act;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * ==============================================================
 * Description: databing activity 基类
 * <p>
 * Created by leonyr on 2019-04-20
 * (C) Copyright LeonyR Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
public abstract class AbBindActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected String TAG;
    protected Context mCtx;
    private T binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new ActivityObserver());

        mCtx = this;
        binding = DataBindingUtil.setContentView(this, getLayoutResId());
        TAG = getClass().getSimpleName();

        initView(savedInstanceState);
    }

    public T Binding() {
        return binding;
    }

    @Override
    protected void onResume() {
        super.onResume();
        TAG = getClass().getSimpleName();
    }

    @Override
    public void onDestroy() {
        mCtx = null;
        super.onDestroy();
    }

    /**
     * 获取layout id
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 初始化其他数据Data
     */
    protected abstract void initView(Bundle savedInstanceState);

}

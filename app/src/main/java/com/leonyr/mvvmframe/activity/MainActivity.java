package com.leonyr.mvvmframe.activity;

import android.arch.lifecycle.ViewModelProviders;

import com.leonyr.lib.mvvm.act.AbActivity;
import com.leonyr.lib.mvvm.vm.LViewModel;
import com.leonyr.lib.mvvm.vm.LViewModelFactory;
import com.leonyr.lib.utils.LogUtil;
import com.leonyr.mvvmframe.R;
import com.leonyr.mvvmframe.vm.MainViewModel;

public class MainActivity extends AbActivity {

    LViewModel vm;

    /**
     * 获取layout id
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化各类view对象
     */
    @Override
    protected void initView() {
        vm = ViewModelProviders.of(this, new LViewModelFactory(this)).get(MainViewModel.class);
        LogUtil.e("main", "test");
    }
}

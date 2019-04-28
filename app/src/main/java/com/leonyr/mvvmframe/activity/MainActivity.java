package com.leonyr.mvvmframe.activity;

import com.leonyr.lib.mvvm.act.AbActivity;
import com.leonyr.lib.mvvm.vm.LViewModel;
import com.leonyr.mvvmframe.R;

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
//        vm = ViewModelProviders.of(this).get(MainViewModel.class);
//        LogUtil.e("main", "test");
    }
}

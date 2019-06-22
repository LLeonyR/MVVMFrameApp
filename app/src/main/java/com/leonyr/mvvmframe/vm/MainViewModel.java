package com.leonyr.mvvmframe.vm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.leonyr.mvvm.net.NetClient;
import com.leonyr.mvvm.net.RxSchedulers;
import com.leonyr.mvvm.vm.LViewModel;
import com.leonyr.lib.utils.LogUtil;
import com.leonyr.mvvmframe.GitHubApi;
import com.leonyr.mvvmframe.model.Momodiy;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * ==============================================================
 * Description:
 * <p>
 * Created by 01385127 on 2019.04.28
 * (C) Copyright sf_Express Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
public class MainViewModel extends LViewModel {

    private String TAG = "MainViewModel";

    public MainViewModel(@NonNull Context c) {
        super(c);
    }

    public void loadData() {
        NetClient.setApiHost("https://api.github.com");
        GitHubApi api = NetClient.build(GitHubApi.class);

        api.gitMomodiy().compose(RxSchedulers.IOMain())
                .subscribe(new Observer<Momodiy>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Momodiy momodiy) {
                        LogUtil.e(TAG, "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "onError" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.e(TAG, "onComplete");
                    }
                });
    }

}

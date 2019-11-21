package com.leonyr.mvvmframe.activity;

import com.leonyr.mvvm.act.AbActivity;
import com.leonyr.lib.utils.LogUtil;
import com.leonyr.mvvmframe.R;
import com.leonyr.mvvmframe.vm.MainViewModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class MainActivity extends AbActivity {

    private static final String TAG = "main tag";

    MainViewModel vm;

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
//        vm = ViewModelProviders.of(this, new LViewModelFactory(this)).get(MainViewModel.class);
//        LogUtil.e("main", "test");

//        vm.loadData();

        Observer<Object> observer1 = new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtil.e(TAG, "1 onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                LogUtil.e(TAG, "1 onNext: " + o.toString());
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG, "1 onError: " + e.toString());
            }

            @Override
            public void onComplete() {
                LogUtil.e(TAG, "1 onComplete");
            }
        };

        Observer<Object> observer2 = new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtil.e(TAG, "2 onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                LogUtil.e(TAG, "2 onNext: " + o.toString());
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG, "2 onError: " + e.toString());
            }

            @Override
            public void onComplete() {
                LogUtil.e(TAG, "2 onComplete");
            }
        };

       /* ReplaySubject<Object> subject = ReplaySubject.create();
        subject.onNext("one");
        subject.onNext("two");
        subject.onNext("three");
        subject.onComplete();

        subject.subscribe(observer1);*/

        /*BehaviorSubject<Object> subject = BehaviorSubject.create();
        subject.onNext("zero");
        subject.onNext("one");
        subject.onNext("two");
//        subject.onComplete();
        subject.onError(new RuntimeException("error"));
        subject.subscribe(observer1);
        subject.onNext("three");*/

        PublishSubject<Object> subject = PublishSubject.create();
        subject.subscribe(observer1);
        subject.onNext("one");
        subject.onNext("two");

        subject.subscribe(observer2);
        subject.onNext("three");
        subject.onComplete();
    }

}

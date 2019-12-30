package com.leonyr.mvvm.view;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author: kermit
 * @date: 2016/7/28
 * Class description:
 * ---- 使用倒计时的页面
 */
public class CountDownControl {

    protected Disposable mDisposable;

    private int COUNT_TIME = 60;//seconds
    private int surplus = COUNT_TIME;
    ICountDownListener listener;
    public boolean running = false;

    public CountDownControl() {
        this.surplus = COUNT_TIME;
    }

    public CountDownControl(int time) {
        COUNT_TIME = time;
        this.surplus = time;
    }

    public CountDownControl(ICountDownListener listener) {
        this.surplus = COUNT_TIME;
        this.listener = listener;
    }

    public CountDownControl(int time, ICountDownListener listener) {
        this.surplus = time;
        COUNT_TIME = time;
        this.listener = listener;
    }

    public void setCountTime(int time){
        this.COUNT_TIME = time;
        surplus = time;
    }

    public void setListener(ICountDownListener listener) {
        this.listener = listener;
    }

    public void start() {

        if (isRunning()){
            return;
        }
        surplus = COUNT_TIME;
        if (null != listener){
            listener.onCountDownOverStart();
        }

        intervalAction();
    }

    private void intervalAction() {
        Observable.interval(1, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (surplus > 0 && surplus <= COUNT_TIME) {
                            surplus --;
                            if (listener != null) {
                                listener.onCountDownProgress(surplus);
                            }
                            running = true;
                        } else {
                            if (null != mDisposable && !mDisposable.isDisposed()) {
                                mDisposable.dispose();
                            }
                            running = false;
                            if (listener != null) {
                                listener.onCountDownOver();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        running = false;
                    }

                    @Override
                    public void onComplete() {
                        running = false;
                    }
                });
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        running = false;
        if (null != mDisposable && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mDisposable = null;
    }

    public void pause(){
        if (null != mDisposable && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public void reStart(){
        intervalAction();
    }

    public interface ICountDownListener {

        void onCountDownOver();

        void onCountDownOverStart();

        void onCountDownProgress(int time);

    }

}

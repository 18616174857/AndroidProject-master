package com.ljy.demo.mvp;

/**
 *    author : Android Liang_liang
 *    time   : 2018/11/17
 *    desc   : MVP 模型基类
 */
public abstract class MvpModel<L> {

    private L mListener;

    public void setListener(L listener) {
        mListener = listener;
    }

    public L getListener() {
        return mListener;
    }
}
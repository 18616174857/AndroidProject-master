package com.ljy.demo.mvp;

import android.content.Context;

/**
 *    author : Android Liang_liang
 *    time   : 2018/11/17
 *    desc   : MVP 通用性接口
 */
public interface IMvpView {

    /**
     * 获取上下文对象
     */
    Context getContext();

    /**
     * 加载中
     */
    void onLoading();

    /**
     * 加载完成
     */
    void onComplete();

    /**
     * 用于请求的数据为空的状态
     */
    void onEmpty();

    /**
     * 用于请求数据出错
     */
    void onError();
}
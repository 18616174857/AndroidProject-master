package com.ljy.demo.mvp.proxy;

/**
 *    author : Android Liang_liang
 *    time   : 2019/05/11
 *    desc   : 逻辑层代理接口
 */
public interface IMvpPresenterProxy {
    /**
     * 绑定 Presenter
     */
    void bindPresenter();

    /**
     * 解绑 Presenter
     */
    void unbindPresenter();
}
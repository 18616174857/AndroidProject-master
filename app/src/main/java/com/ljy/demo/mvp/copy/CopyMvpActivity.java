package com.ljy.demo.mvp.copy;

import android.view.View;

import com.ljy.demo.R;
import com.ljy.demo.mvp.MvpActivity;
import com.ljy.demo.mvp.MvpInject;

import java.util.List;

/**
 *    author : Android Liang_liang
 *    time   : 2018/11/17
 *    desc   : 可进行拷贝的MVP Activity 类
 */
public final class CopyMvpActivity extends MvpActivity implements CopyContract.View {

    @MvpInject
    CopyPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_copy;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public void onLogin(View view) {
        // 登录操作
        mPresenter.login("账户", "密码");
    }

    /**
     * {@link CopyContract.View}
     */

    @Override
    public void loginError(String msg) {
        toast(msg);
    }

    @Override
    public void loginSuccess(List<String> data) {
        toast("登录成功了");
    }
}
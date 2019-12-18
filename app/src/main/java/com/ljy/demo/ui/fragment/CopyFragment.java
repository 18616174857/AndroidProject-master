package com.ljy.demo.ui.fragment;

import com.ljy.demo.R;
import com.ljy.demo.common.MyLazyFragment;
import com.ljy.demo.ui.activity.CopyActivity;

/**
 *    author : Android Liang_liang
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public final class CopyFragment extends MyLazyFragment<CopyActivity> {

    public static CopyFragment newInstance() {
        return new CopyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_copy;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
package com.ljy.demo.ui.fragment;

import android.view.View;

import com.ljy.demo.R;
import com.ljy.demo.common.MyLazyFragment;
import com.ljy.demo.ui.activity.HomeActivity;
import com.ljy.widget.view.CountdownView;
import com.ljy.widget.view.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *    author : Android Liang_liang
 *    time   : 2018/10/18
 *    desc   : 项目自定义控件展示
 */
public final class TestFragmentB extends MyLazyFragment<HomeActivity>
        implements SwitchButton.OnCheckedChangeListener {

    @BindView(R.id.sb_test_switch)
    SwitchButton mSwitchButton;

    @BindView(R.id.cv_test_countdown)
    CountdownView mCountdownView;

    public static TestFragmentB newInstance() {
        return new TestFragmentB();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_b;
    }

    @Override
    protected void initView() {
        mSwitchButton.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.cv_test_countdown)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_test_countdown:
                toast(R.string.common_code_send_hint);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    /**
     * {@link SwitchButton.OnCheckedChangeListener}
     */

    @Override
    public void onCheckedChanged(SwitchButton button, boolean isChecked) {
        toast(isChecked);
    }
}
package com.ljy.demo.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ljy.demo.R;
import com.ljy.demo.common.MyActivity;
import com.ljy.demo.helper.InputTextHelper;
import com.ljy.widget.view.CountdownView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *    author : Android Liang_liang
 *    time   : 2019/04/20
 *    desc   : 校验手机号
 */
public final class PhoneVerifyActivity extends MyActivity {

    @BindView(R.id.tv_phone_verify_phone)
    TextView mPhoneView;

    @BindView(R.id.et_phone_verify_code)
    EditText mCodeView;
    @BindView(R.id.cv_phone_verify_countdown)
    CountdownView mCountdownView;
    @BindView(R.id.btn_phone_verify_commit)
    Button mCommitView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_verify;
    }

    @Override
    protected void initView() {
        InputTextHelper.with(this)
                .addView(mCodeView)
                .setMain(mCommitView)
                .setListener(new InputTextHelper.OnInputTextListener() {

                    @Override
                    public boolean onInputChange(InputTextHelper helper) {
                        return mCodeView.getText().toString().length() == 4;
                    }
                })
                .build();
    }

    @Override
    protected void initData() {
        mPhoneView.setText(String.format(getString(R.string.phone_verify_current_phone), "18888888888"));
    }

    @OnClick({R.id.cv_phone_verify_countdown, R.id.btn_phone_verify_commit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_phone_verify_countdown:
                // 获取验证码
                if (mPhoneView.getText().toString().length() != 11) {
                    // 重置验证码倒计时控件
                    mCountdownView.resetState();
                    toast(R.string.common_phone_input_error);
                } else {
                    toast(R.string.common_code_send_hint);
                }
                break;
            case R.id.btn_phone_verify_commit:
                // 修改手机号
                startActivityFinish(PhoneResetActivity.class);
                break;
            default:
                break;
        }
    }
}
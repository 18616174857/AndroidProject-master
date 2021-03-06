package com.ljy.demo.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.ljy.base.BaseDialog;
import com.ljy.demo.R;
import com.ljy.demo.common.MyLazyFragment;
import com.ljy.demo.ui.activity.AboutActivity;
import com.ljy.demo.ui.activity.DialogActivity;
import com.ljy.demo.ui.activity.HomeActivity;
import com.ljy.demo.ui.activity.ImageActivity;
import com.ljy.demo.ui.activity.ListViewActivity;
import com.ljy.demo.ui.activity.LoginActivity;
import com.ljy.demo.ui.activity.PasswordForgetActivity;
import com.ljy.demo.ui.activity.PasswordResetActivity;
import com.ljy.demo.ui.activity.PersonalDataActivity;
import com.ljy.demo.ui.activity.PhoneResetActivity;
import com.ljy.demo.ui.activity.PhoneVerifyActivity;
import com.ljy.demo.ui.activity.RecyclerViewActivity;
import com.ljy.demo.ui.activity.RegisterActivity;
import com.ljy.demo.ui.activity.SettingActivity;
import com.ljy.demo.ui.activity.SlideMoreRecyclerViewActivity;
import com.ljy.demo.ui.activity.SlideRecyclerViewActivity;
import com.ljy.demo.ui.activity.StatusActivity;
import com.ljy.demo.ui.activity.UpLoadImageActivity;
import com.ljy.demo.ui.activity.ViewPageActivity;
import com.ljy.demo.ui.activity.WebActivity;
import com.ljy.demo.ui.dialog.MessageDialog;

import java.util.ArrayList;

import butterknife.OnClick;

/**
 *    author : Android Liang_liang
 *    time   : 2018/10/18
 *    desc   : 项目界面跳转示例
 */
public final class TestFragmentD extends MyLazyFragment<HomeActivity> {

    public static TestFragmentD newInstance() {
        return new TestFragmentD();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_d;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_test_dialog, R.id.btn_test_hint,R.id.btn_test_up, R.id.btn_test_login, R.id.btn_test_register, R.id.btn_test_forget,
            R.id.btn_test_reset,R.id.btn_test_verify, R.id.btn_test_change, R.id.btn_test_personal, R.id.btn_test_setting,
            R.id.btn_test_about, R.id.btn_test_browser, R.id.btn_test_image, R.id.btn_test_crash, R.id.btn_test_pay,
            R.id.btn_test_RecyclerView, R.id.btn_test_RecyclerView2,R.id.btn_test_RecyclerView3,R.id.btn_test_ListView,
            R.id.btn_test_ViewPage})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test_dialog:
                startActivity(DialogActivity.class);
                break;
            case R.id.btn_test_hint:
                startActivity(StatusActivity.class);
                break;
            case R.id.btn_test_up:
                startActivity(UpLoadImageActivity.class);
                break;
            case R.id.btn_test_login:
                startActivity(LoginActivity.class);
                break;
            case R.id.btn_test_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.btn_test_forget:
                startActivity(PasswordForgetActivity.class);
                break;
            case R.id.btn_test_reset:
                startActivity(PasswordResetActivity.class);
                break;
            case R.id.btn_test_verify:
                startActivity(PhoneVerifyActivity.class);
                break;
            case R.id.btn_test_change:
                startActivity(PhoneResetActivity.class);
                break;
            case R.id.btn_test_personal:
                startActivity(PersonalDataActivity.class);
                break;
            case R.id.btn_test_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.btn_test_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.btn_test_browser:
                WebActivity.start(getAttachActivity(), "https://github.com/getActivity/");
                break;
            case R.id.btn_test_image:
                ArrayList<String> images = new ArrayList<>();
                images.add("https://www.baidu.com/img/bd_logo.png");
                images.add("https://avatars1.githubusercontent.com/u/28616817?s=460&v=4");
                ImageActivity.start(getAttachActivity(), images, images.size() - 1);
                break;
            case R.id.btn_test_crash:
                throw new IllegalStateException("are you ok?");
            case R.id.btn_test_pay:
                new MessageDialog.Builder(getAttachActivity())
                        .setTitle("捐赠")
                        .setMessage("如果您觉得这个开源项目很棒，希望它能更好地坚持开发下去，可否愿意花一点点钱（推荐 10.24 元）作为对于开发者的激励")
                        .setConfirm("支付宝")
                        .setCancel(null)
                        //.setAutoDismiss(false)
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(BaseDialog dialog) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("alipays://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2FFKX03758FLJF0OA16ISZD2%3F_s%3Dweb-other"));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    toast("这个开源项目因为您的支持而能够不断更新、完善，非常感谢您的支持");
                                } catch (Exception e) {
                                    toast("打开支付宝失败");
                                }
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {}
                        })
                        .show();
                break;
            case R.id.btn_test_RecyclerView:

                startActivity(RecyclerViewActivity.class);
                break;
            case R.id.btn_test_RecyclerView2:

                startActivity(SlideRecyclerViewActivity.class);
                break;
            case R.id.btn_test_RecyclerView3:

                startActivity(SlideMoreRecyclerViewActivity.class);
                break;
            case R.id.btn_test_ListView:
                startActivity(ListViewActivity.class);
                break;
            case R.id.btn_test_ViewPage:

                startActivity(ViewPageActivity.class);
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
}
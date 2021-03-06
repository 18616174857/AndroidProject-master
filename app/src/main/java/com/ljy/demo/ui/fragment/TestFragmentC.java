package com.ljy.demo.ui.fragment;

import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.ljy.demo.R;
import com.ljy.demo.common.MyLazyFragment;
import com.ljy.demo.ui.activity.HomeActivity;
import com.ljy.demo.ui.activity.PhotoActivity;
import com.ljy.image.ImageLoader;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *    author : Android Liang_liang
 *    time   : 2018/10/18
 *    desc   : 项目框架使用示例
 */
public final class TestFragmentC extends MyLazyFragment<HomeActivity> {

    @BindView(R.id.iv_test_image)
    ImageView mImageView;
    String ImageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1578463628239&di=25e922850f4bf05c434d49d8ef58c68a&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171211%2F40e91083ba0e4a0084b2c80857195050.jpeg";

    public static TestFragmentC newInstance() {
        return new TestFragmentC();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_c;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @OnClick({R.id.btn_test_image1, R.id.btn_test_image2, R.id.btn_test_image3, R.id.btn_test_image4,
            R.id.btn_test_toast, R.id.btn_test_permission, R.id.btn_test_state_black, R.id.btn_test_state_white})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test_image1:
                mImageView.setVisibility(View.VISIBLE);
                ImageLoader.with(this)
                        .load(ImageUrl)
                        .into(mImageView);
                break;
            case R.id.btn_test_image2:
                mImageView.setVisibility(View.VISIBLE);
                ImageLoader.with(this)
                        .circle()
                        .load(ImageUrl)
                        .into(mImageView);
                break;
            case R.id.btn_test_image3:
                mImageView.setVisibility(View.VISIBLE);
                ImageLoader.with(this)
                        .circle((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, this.getResources().getDisplayMetrics()))
                        .load(ImageUrl)
                        .into(mImageView);
                break;
            case R.id.btn_test_image4:
                PhotoActivity.start(getAttachActivity(), new PhotoActivity.OnPhotoSelectListener() {

                    @Override
                    public void onSelect(List<String> data) {
                        mImageView.setVisibility(View.VISIBLE);
                        ImageLoader.with(getAttachActivity())
                                .load(data.get(0))
                                .into(mImageView);
                    }

                    @Override
                    public void onCancel() {
                        toast("取消了");
                    }
                });
                break;
            case R.id.btn_test_toast:
                toast("我是吐司");
                break;
            case R.id.btn_test_permission:
                XXPermissions.with(getAttachActivity())
                        // 可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                        //.constantRequest()
                        // 支持请求6.0悬浮窗权限8.0请求安装权限
                        //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES)
                        // 不指定权限则自动获取清单中的危险权限
                        .permission(Permission.CAMERA)
                        .request(new OnPermission() {

                            @Override
                            public void hasPermission(List<String> granted, boolean isAll) {
                                if (isAll) {
                                    toast("获取权限成功");
                                } else {
                                    toast("获取权限成功，部分权限未正常授予");
                                }
                            }

                            @Override
                            public void noPermission(List<String> denied, boolean quick) {
                                if(quick) {
                                    toast("被永久拒绝授权，请手动授予权限");
                                    //如果是被永久拒绝就跳转到应用权限系统设置页面
                                    XXPermissions.gotoPermissionSettings(getAttachActivity());
                                } else {
                                    toast("获取权限失败");
                                }
                            }
                        });
                break;
            case R.id.btn_test_state_black:
                //状态栏黑色字体
                getAttachActivity().getStatusBarConfig().statusBarDarkFont(true).init();
                break;
            case R.id.btn_test_state_white:
                //状态栏白色字体
                getAttachActivity().getStatusBarConfig().statusBarDarkFont(false).init();
                break;
            default:
                break;
        }
    }
}
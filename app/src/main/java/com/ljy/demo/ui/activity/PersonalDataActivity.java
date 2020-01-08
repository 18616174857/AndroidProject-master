package com.ljy.demo.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.ljy.base.BaseDialog;
import com.ljy.demo.R;
import com.ljy.demo.common.MyActivity;
import com.ljy.demo.ui.dialog.AddressDialog;
import com.ljy.demo.ui.dialog.InputDialog;
import com.ljy.demo.widget.MyImageViewCircle;
import com.ljy.image.ImageLoader;
import com.ljy.widget.layout.SettingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : Android Liang_liang
 * time   : 2019/04/20
 * desc   : 个人资料
 */
public final class PersonalDataActivity extends MyActivity {

    @BindView(R.id.iv_person_data_avatar)
    ImageView mAvatarView;
    @BindView(R.id.sb_person_data_id)
    SettingBar mIDView;
    @BindView(R.id.sb_person_data_name)
    SettingBar mNameView;
    @BindView(R.id.sb_person_data_address)
    SettingBar mAddressView;
    @BindView(R.id.sb_person_data_phone)
    SettingBar mPhoneView;
    @BindView(R.id.iv_person_data_circle)
    MyImageViewCircle ivPersonDataCircle;

    private String mAvatarUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_data;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_person_data_avatar,R.id.iv_person_data_circle, R.id.fl_person_data_head, R.id.sb_person_data_name, R.id.sb_person_data_address, R.id.sb_person_data_phone})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_person_data_avatar:
                if (mAvatarUrl != null && !"".equals(mAvatarUrl)) {
                    ImageLoader.with(getActivity())
                            .load(mAvatarUrl)
                            .into(mAvatarView);
                    // 查看头像
                    ImageActivity.start(getActivity(), mAvatarUrl);
                } else {
                    // 选择头像
                    onClick(findViewById(R.id.fl_person_data_head));
                }
                break;
            case R.id.iv_person_data_circle:
                if (mAvatarUrl != null && !"".equals(mAvatarUrl)) {
                    ImageLoader.with(getActivity())
                            .load(mAvatarUrl)
                            .into(ivPersonDataCircle);
                    // 查看头像
                    ImageActivity.start(getActivity(), mAvatarUrl);
                } else {
                    // 选择头像
                    onClick(findViewById(R.id.fl_person_data_head));
                }
                break;
            case R.id.fl_person_data_head:
                PhotoActivity.start(getActivity(), new PhotoActivity.OnPhotoSelectListener() {

                    @Override
                    public void onSelect(List<String> data) {
                        mAvatarUrl = data.get(0);
                        ImageLoader.with(getActivity())
                                .load(mAvatarUrl)
                                .into(ivPersonDataCircle);
                        //Glide加载圆形和圆角
                        /*Glide.with(itemView.getContext()).load(R.mipmap.female).
                                apply(RequestOptions.bitmapTransform(new CircleCrop())).into(myClassImg);*/
                        /*int defaultHead = R.mipmap.female;
                        if (!TextUtils.isEmpty(schoolDirectoryPersonDetailsBean.getTeamMemberGender())
                                && schoolDirectoryPersonDetailsBean.getTeamMemberGender().equals("男")) {
                            defaultHead = R.mipmap.male;
                        } else {
                            defaultHead = R.mipmap.female;
                        }
                        RequestOptions requestOptions = RequestOptions
                                .bitmapTransform(new RoundedCorners(10))//显示圆角
                                .placeholder(defaultHead)
                                .error(defaultHead);//加载不出显示错误图片
                        Glide.with(getBaseContext()).load(schoolDirectoryPersonDetailsBean.getTeamMemberImage())
                                .apply(requestOptions).into(ivImgUrl);*/
                    }

                    @Override
                    public void onCancel() {
                    }
                });
                break;
            case R.id.sb_person_data_name:
                new InputDialog.Builder(this)
                        // 标题可以不用填写
                        .setTitle(getString(R.string.personal_data_name_hint))
                        .setContent(mNameView.getRightText())
                        //.setHint(getString(R.string.personal_data_name_hint))
                        //.setConfirm("确定")
                        // 设置 null 表示不显示取消按钮
                        //.setCancel("取消")
                        // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setListener(new InputDialog.OnListener() {

                            @Override
                            public void onConfirm(BaseDialog dialog, String content) {
                                if (!mNameView.getRightText().equals(content)) {
                                    mNameView.setRightText(content);
                                }
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                            }
                        })
                        .show();
                break;
            case R.id.sb_person_data_address:
                new AddressDialog.Builder(this)
                        //.setTitle("选择地区")
                        // 设置默认省份
                        .setProvince("江苏省")
                        // 设置默认城市（必须要先设置默认省份）
                        .setCity("苏州市")
                        // 不选择县级区域
                        //.setIgnoreArea()
                        .setListener(new AddressDialog.OnListener() {

                            @Override
                            public void onSelected(BaseDialog dialog, String province, String city, String area) {
                                String address = province + city + area;
                                if (!mAddressView.getRightText().equals(address)) {
                                    mAddressView.setRightText(address);
                                }
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                            }
                        })
                        .show();
                break;
            case R.id.sb_person_data_phone:
                // 先判断有没有设置过手机号
                if (true) {
                    startActivity(PhoneVerifyActivity.class);
                } else {
                    startActivity(PhoneResetActivity.class);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
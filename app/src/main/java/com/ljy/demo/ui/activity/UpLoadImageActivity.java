package com.ljy.demo.ui.activity;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.ljy.base.BaseDialog;
import com.ljy.base.BaseRecyclerViewAdapter;
import com.ljy.demo.R;
import com.ljy.demo.common.MyActivity;
import com.ljy.demo.other.AppConfig;
import com.ljy.demo.other.PhotoSpaceDecoration;
import com.ljy.demo.ui.adapter.UpLoadAdapter;
import com.ljy.demo.ui.dialog.MenuDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UpLoadImageActivity extends MyActivity implements BaseRecyclerViewAdapter.OnChildClickListener{

    @BindView(R.id.srl_test)
    SmartRefreshLayout srlTest;
    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    @BindView(R.id.btn_test_hint)
    AppCompatButton btnTestHint;
    private UpLoadAdapter mTestAdapter;
    /**
     * 选中列表
     */
    private final ArrayList<String> mSelectPhoto = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_up_load_image;
    }

    @Override
    protected void initView() {
        btnTestHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   initDialog();
            }
        });
        refreshView();
        smartRefreshView();
    }

    @Override
    protected void initData() {

    }

    /**
     * 刷新消息列表
     */
    private void refreshView() {

        mTestAdapter = new UpLoadAdapter(this,mSelectPhoto);
        mTestAdapter.setOnChildClickListener(R.id.fl_photo_check, this);
        mTestAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                ImageActivity.start(getActivity(), mSelectPhoto,position);
            }
        });
        rvTest.setAdapter(mTestAdapter);
        // 禁用动画效果
        rvTest.setItemAnimator(null);
        // 添加分割线
        //解决数据加载不完的问题
        rvTest.setNestedScrollingEnabled(false);
        //当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小
        rvTest.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        rvTest.setFocusable(false);
        // 添加分割线
        rvTest.addItemDecoration(new PhotoSpaceDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics())));

    }

    /**
     * MainActivity中增加下拉刷新和上拉加载的监听方法
     */
    private void smartRefreshView() {
        srlTest.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉刷新,一般添加调用接口获取数据的方法
                refreshLayout.finishRefresh();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载，一般添加调用接口获取更多数据的方法
                refreshLayout.finishLoadMoreWithNoMoreData();


            }
        });
    }

    private void initDialog(){
        List<String> data = new ArrayList<>();
               /* for (int i = 0; i < 5; i++) {
                    data.add("我是数据" + i);
                }*/
        data.add("拍照");
        data.add("从手机相册选择");
        // 底部选择框
        new MenuDialog.Builder(getActivity())
                // 设置 null 表示不显示取消按钮
                //.setCancel(getString(R.string.common_cancel))
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setList(data)
                .setListener(new MenuDialog.OnListener<String>() {

                    @Override
                    public void onSelected(BaseDialog dialog, int position, String string) {
                        if(position == 0){
                            // 点击拍照
                            Permissions();
                        }else if(position ==1){
                            PhotoActivity.start(getActivity(), new PhotoActivity.OnPhotoSelectListener() {

                                @Override
                                public void onSelect(List<String> data) {
                                      for(int i =0; i<data.size();i++){
                                          mSelectPhoto.add(data.get(i));
                                      }
                                    mTestAdapter.setData(mSelectPhoto);
                                }

                                @Override
                                public void onCancel() {
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                        toast("取消了");
                    }
                })
                .show();
    }
    private void Permissions(){
            XXPermissions.with(this)
                    .permission(Permission.CAMERA)
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            // 点击拍照
                            launchCamera();
                        }
                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            if (quick) {
                                toast(R.string.common_permission_fail);
                                XXPermissions.gotoPermissionSettings(UpLoadImageActivity.this, true);
                            } else {
                                toast(R.string.common_permission_hint);
                            }
                        }
                    });

    }
    /**
     * 启动系统相机
     */
    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File file = createCameraFile();
            if (file != null && file.exists()) {


                Uri imageUri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // 通过 FileProvider 创建一个 Content 类型的 Uri 文件
                    imageUri = FileProvider.getUriForFile(this, AppConfig.getPackageName() + ".provider", file);
                } else {
                    imageUri = Uri.fromFile(file);
                }
                // 对目标应用临时授权该 Uri 所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                // 将拍取的照片保存到指定 Uri
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, new ActivityCallback() {

                    @SuppressWarnings("ResultOfMethodCallIgnored")
                    @Override
                    public void onActivityResult(int resultCode, @Nullable Intent data) {
                        switch (resultCode) {
                            case RESULT_OK:
                                if (file.exists() && file.isFile()) {
                                    // 重新扫描多媒体（否则可能扫描不到）
                                    MediaScannerConnection.scanFile(getApplicationContext(), new String[]{file.getPath()}, null,null);

                                    // 当前选中图片的数量必须小于最大选中数
                                   /*   if (mSelectPhoto.size() < mMaxSelect) {
                                        mSelectPhoto.add(file.getPath());
                                    }*/
                                    mSelectPhoto.add(file.getPath());
                                    mTestAdapter.setData(mSelectPhoto);
                                }
                                break;
                            case RESULT_CANCELED:
                                file.delete();
                                break;
                            default:
                                break;
                        }
                    }
                });
            } else {
                toast(R.string.photo_picture_error);
            }
        } else {
            toast(R.string.photo_launch_fail);
        }
    }
    /**
     * 创建一个拍照图片文件对象
     */
    private File createCameraFile() {
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "自定义控件拍照相册");
        if (!folder.exists() || !folder.isDirectory()) {
            if (!folder.mkdirs()) {
                folder = Environment.getExternalStorageDirectory();
            }
        }

        try {
            return File.createTempFile("IMG", ".jpg", folder);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * {@link BaseRecyclerViewAdapter.OnChildClickListener}
     * @param recyclerView      RecyclerView对象
     * @param childView         被点击的条目子 View Id
     * @param position          被点击的条目位置
     */
    @Override
    public void onChildClick(RecyclerView recyclerView, View childView, int position) {
        switch (childView.getId()) {
            case R.id.fl_photo_check:
                if (mSelectPhoto.contains(mSelectPhoto.get(position))) {
                    mSelectPhoto.remove(mSelectPhoto.get(position));

                    if (mSelectPhoto.isEmpty()) {
                       //显示空布局
                    }
                }
                mTestAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}

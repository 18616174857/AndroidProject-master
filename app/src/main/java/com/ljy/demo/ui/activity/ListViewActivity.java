package com.ljy.demo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ljy.demo.R;
import com.ljy.demo.common.MyActivity;
import com.ljy.demo.ui.api.CustomerConstant;
import com.ljy.demo.ui.dialog.LoadingDialog;
import com.ljy.demo.until.PreferencesUtils;
import com.ljy.demo.widget.SwipeListLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

public class ListViewActivity extends MyActivity {
    @BindView(R.id.project_list)
    ListView projectList;
    @BindView(R.id.srl_test)
    SmartRefreshLayout srlTest;
    private Set<SwipeListLayout> sets = new HashSet();
    List<String> list = new ArrayList<String>();
    ListviewAdapter  adapter ;
    LoadingDialog loadingProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_view;
    }

    @Override
    protected void initView() {
        smartRefreshView();
       // UserLogin();
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("测试");
        }
        adapter = new ListviewAdapter(getActivity(), list);
        projectList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    //当listview开始滑动时，若有item的状态为Open，则Close，然后移除
                    case SCROLL_STATE_TOUCH_SCROLL:
                        if (sets.size() > 0) {
                            for (SwipeListLayout s : sets) {
                                s.setStatus(SwipeListLayout.Status.Close, true);
                                sets.remove(s);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        projectList.setAdapter(adapter);
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


    /*private void UserLogin() {
        loadingProgress.show();
        OkGo.<UserInfo>post(CustomerConstant.COMMONURL+"getUserInfo")

                .tag(this)
                .params("name", loginusername)
                .params("token", userToken)
                .execute(new JsonCallback<UserInfo>() {
                    @Override
                    public void onSuccess(Response<UserInfo> jsonResult) {
                        loadingProgress.dismiss();
                        if (jsonResult.body().getStatuscode().equals("01")) {
                            // TODO: 2018/3/19 0019 极光ID
                            PreferencesUtils.putString(mContext, "username", loginusername);
                            PreferencesUtils.putString(mContext, "password", loginpassword);
                            PreferencesUtils.putBoolean(mContext, "islogin", true);
                            Intent it = new Intent(mContext, CompanyActivity.class);
                            startActivity(it);
                            finish();
                        } else {
                            Toast.makeText(CompanyLoginActivity.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<UserInfo> response) {
                        loadingProgress.dismiss();
                        showNetTipsDialog();
                    }
                });
    }*/


    public class ListviewAdapter extends BaseAdapter {
        private List<String> list;
        private Context mContext;

        public ListviewAdapter(Context mContext, List<String> list) {
            this.mContext = mContext;
            this.list = list;
        }

        public void updateData(List<String> data) {
            list.clear();
            if (data != null) {
                list.addAll(data);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewholder;
            if (convertView == null) {
                viewholder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_test, null);
                viewholder.sll_main = (SwipeListLayout) convertView.findViewById(R.id.sll_main);
                viewholder.image = (ImageView) convertView.findViewById(R.id.image);
                viewholder.progress = (TextView) convertView.findViewById(R.id.progress);
                viewholder.mRelativeLayout = (RelativeLayout)convertView.findViewById(R.id.mRelativeLayout) ;
                //详情
                viewholder.iv_edit = (Button) convertView.findViewById(R.id.iv_edit);
                convertView.setTag(viewholder);
            } else {
                viewholder = (ViewHolder) convertView.getTag();
            }
            viewholder.sll_main.setOnSwipeStatusListener(new MyOnSlipStatusListener(viewholder.sll_main));



            return convertView;
        }

        class ViewHolder {
            Button iv_edit;
            SwipeListLayout sll_main;
            TextView  progress;
            ImageView image;
            RelativeLayout mRelativeLayout;
        }
    }
    public class MyOnSlipStatusListener implements SwipeListLayout.OnSwipeStatusListener{
        private SwipeListLayout slipListLayout;
        public MyOnSlipStatusListener(SwipeListLayout slipListLayout) {
            this.slipListLayout = slipListLayout;
        }

        @Override
        public void onStatusChanged(SwipeListLayout.Status status) {
            if (status == SwipeListLayout.Status.Open) {
                //若有其他的item的状态为Open，则Close，然后移除
                if (sets.size() > 0) {
                    for (SwipeListLayout s : sets) {
                        s.setStatus(SwipeListLayout.Status.Close, true);
                        sets.remove(s);
                    }
                }
                sets.add(slipListLayout);
            } else {
                if (sets.contains(slipListLayout)) {
                    sets.remove(slipListLayout);
                }
            }
        }

        @Override
        public void onStartCloseAnimation() {

        }

        @Override
        public void onStartOpenAnimation() {

        }

    }
}

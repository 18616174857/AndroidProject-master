package com.ljy.demo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ljy.demo.R;
import com.ljy.demo.common.MyActivity;
import com.ljy.demo.ui.Entity.TestEntity;
import com.ljy.demo.ui.adapter.TestAdapter;
import com.ljy.demo.widget.SwipeListLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *    author : Android Liang_liang
 *    time   : 2020/01/08
 *    desc   : RecyclerView使用案例
 */
public class RecyclerViewActivity extends MyActivity {


    @BindView(R.id.srl_test)
    SmartRefreshLayout srlTest;
    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    private TestAdapter mTestAdapter;
    private ArrayList<TestEntity.ResultBean.ListBean> mTestList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initView() {
        //初始化的时候默认没有数据，显示空的布局
         getData(1);
        refreshView();
        smartRefreshView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 刷新消息列表
     */
    private void refreshView() {
        //1,加载空布局文件，便于第五步适配器在没有数据的时候加载
        View emptyView = View.inflate(this, R.layout.empty_view, null);

        //2，设置LayoutManager,LinearLayoutManager表示竖直向下
        rvTest.setLayoutManager(new LinearLayoutManager(this));
        //3，初始化一个无数据的适配器
        mTestAdapter = new TestAdapter(mTestList);
        //开启动画效果
        mTestAdapter.openLoadAnimation();
        //设置动画效果
        /**
         * 渐显 ALPHAIN
         * 缩放 SCALEIN
         * 从下到上 SLIDEIN_BOTTOM
         * 从左到右 SLIDEIN_LEFT
         * 从右到左 SLIDEIN_RIGHT
         */
        mTestAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //4，绑定recyclerView和适配器
        rvTest.setAdapter(mTestAdapter);
        //5，给recyclerView设置空布局
        mTestAdapter.setEmptyView(emptyView);

        //解决数据加载不完的问题
        rvTest.setNestedScrollingEnabled(false);
        //当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小
        rvTest.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        rvTest.setFocusable(false);
        //6，给recyclerView的每一个子列表添加点击事件
        mTestAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TextView title = view.findViewById(R.id.test_item_title);
             /*   switch (view.getId()){
                   case R.id.test_item_title:
                        Toast.makeText(getBaseContext(), "我点击了第个的标题",
                        Toast.LENGTH_SHORT).show();
                       break;
                   case R.id.test_item_message:
                       Toast.makeText(getBaseContext(), "我点击了第个的内容",
                               Toast.LENGTH_SHORT).show();
                       break;
               }*/
            }
        });
    }

    /**
     * MainActivity中增加下拉刷新和上拉加载的监听方法
     */
    private void smartRefreshView() {
        srlTest.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉刷新,一般添加调用接口获取数据的方法
                getData(2);
                refreshLayout.finishRefresh();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载，一般添加调用接口获取更多数据的方法
                getData(3);
                refreshLayout.finishLoadMoreWithNoMoreData();


            }
        });
    }

    /**
     * 获取数据的方法
     * 该方法纯属展示各种效果，实际应用时候请自己根据需求做判断即可
     *
     * @param mode 模式：1为刚开始进来加载数据 空数据 2为下拉刷新 3为上拉加载
     */
    private void getData(int mode) {
        //添加临时数据，一般直接从接口获取
        switch (mode) {
            case 1:
                break;
            case 2:
                mTestList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    mTestList.add(new TestEntity.ResultBean.ListBean("我是布丁", "我有一个小狗布丁呀"));
                }
                //更新数据
                mTestAdapter.setNewData(mTestList);
                break;
            case 3:
                for (int i = 0; i < 10; i++) {
                    mTestList.add(new TestEntity.ResultBean.ListBean("我是布丁", "我有一个小狗布丁呀"));
                }
                mTestAdapter.setNewData(mTestList);

                break;
            default:
                mTestList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    mTestList.add(new TestEntity.ResultBean.ListBean("我有一个小狗", "我有一个小狗我有一个小狗我有一个小狗我有一个小狗我有一个小狗"));
                }
                break;
        }
    }


}

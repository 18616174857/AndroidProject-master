package com.ljy.demo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ljy.demo.R;
import com.ljy.demo.common.MyActivity;
import com.ljy.demo.ui.Entity.TestEntity;
import com.ljy.demo.ui.adapter.SlideRecyclerViewAdapter;
import com.ljy.demo.widget.SlideRecyclerView;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import java.util.ArrayList;
import butterknife.BindView;
/**
 *    author : Android Liang_liang
 *    time   : 2020/01/08
 *    desc   : 单个侧滑删除RecyclerView使用案例
 */
public class SlideRecyclerViewActivity extends MyActivity {

    @BindView(R.id.recycler_view_list)
    SlideRecyclerView recyclerViewList;
    @BindView(R.id.srl_test)
    SmartRefreshLayout srlTest;
    private SlideRecyclerViewAdapter mTestAdapter;
    private ArrayList<TestEntity.ResultBean.ListBean> mTestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_slide_recycler_view;
    }

    @Override
    protected void initView() {
     /*   mTestList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mTestList.add(new TestEntity.ResultBean.ListBean("我是布丁--" + i + "", "我有一个小狗布丁呀"));
        }*/
        mTestList = new ArrayList<>();
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
        //1,加载空布局文件，便于第五步适配器在没有数据的时候加载
        View emptyView = View.inflate(this, R.layout.empty_view, null);
        //2，设置LayoutManager,LinearLayoutManager表示竖直向下
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));

        //3，初始化一个无数据的适配器
        mTestAdapter = new SlideRecyclerViewAdapter(mTestList);
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
        recyclerViewList.setAdapter(mTestAdapter);
        //5，给recyclerView设置空布局
        mTestAdapter.setEmptyView(emptyView);
        //解决数据加载不完的问题
        recyclerViewList.setNestedScrollingEnabled(false);
        //当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小
        recyclerViewList.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerViewList.setFocusable(false);
        //6，给recyclerView的每一个子列表添加点击事件
        mTestAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "我点击了详情==="+position+"",
                        Toast.LENGTH_SHORT).show();
            }
        });
        mTestAdapter.setOnDetailsClickListener(new SlideRecyclerViewAdapter.DetailsItemListener() {
            @Override
            public void onDetailsClick(int position) {
                Toast.makeText(getActivity(), "我点击了编辑==="+position+"",
                        Toast.LENGTH_SHORT).show();
              //  recyclerViewList.closeMenu();
            }
        });
        mTestAdapter.setOnDeleteClickListener(new SlideRecyclerViewAdapter.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                if (position >= 0 && position < mTestList.size()) {
                    mTestList.remove(position);
                    mTestAdapter.notifyItemRemoved(position);
                }
                Toast.makeText(getActivity(), "我点击了删除****"+position+"",
                        Toast.LENGTH_SHORT).show();
               // mTestAdapter.notifyDataSetChanged();
                recyclerViewList.closeMenu();
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
                mTestList.clear();
                getData();
                refreshLayout.finishRefresh();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载，一般添加调用接口获取更多数据的方法
                if(mTestList.size()<20){
                    getData();
                    refreshLayout.finishLoadMore();
                }else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        });
    }
   private void getData(){
       for (int i = 10; i < 20; i++) {
           mTestList.add(new TestEntity.ResultBean.ListBean("我是布丁"+i+"", "我有一个小狗布丁呀"));
       }
       //更新数据
       mTestAdapter.setNewData(mTestList);
   }
}

package com.ljy.demo.ui.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;

import com.ljy.demo.R;
import com.ljy.demo.common.MyActivity;
import com.ljy.demo.ui.adapter.RecAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import cn.we.swipe.helper.WeSwipe;
import cn.we.swipe.helper.WeSwipeHelper;
/**
 *    author : Android Liang_liang
 *    time   : 2020/01/08
 *    desc   : 侧滑多个菜单RecyclerView使用案例
 */
public class SlideMoreRecyclerViewActivity extends MyActivity {
    @BindView(R.id.recycler_view_list)
    RecyclerView recyclerViewList;
    @BindView(R.id.srl_test)
    SmartRefreshLayout srlTest;
    RecAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_slide_more_recycler_view;
    }

    @Override
    protected void initView() {

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
        //设置LayoutManager,LinearLayoutManager表示竖直向下
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        //初始化一个适配器
        adapter = new RecAdapter(this);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("Item  " +i);
        }
        adapter.setList(list);
        //4，绑定recyclerView和适配器
        recyclerViewList.setAdapter(adapter);
        //解决数据加载不完的问题
        recyclerViewList.setNestedScrollingEnabled(false);
        //当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小
        recyclerViewList.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerViewList.setFocusable(false);
        //侧滑删除一定要设置WeSwipe。
        WeSwipe.attach(recyclerViewList).setType(WeSwipeHelper.SWIPE_ITEM_TYPE_FLOWING);
        //删除的点击事件。
        adapter.setDelectedItemListener(new RecAdapter.DeletedItemListener() {
            @Override
            public void deleted(int position) {

                adapter.removeDataByPosition(position);
            }
        });
        //置顶的点击事件。
        adapter.setTopItemListener(new RecAdapter.TopItemListener() {
            @Override
            public void top(int position) {
                Toast.makeText(getActivity(), "我点击了置顶",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //详情的点击事件。
        adapter.setDetailsItemListener(new RecAdapter.DetailsItemListener() {
            @Override
            public void details(int position) {
                Toast.makeText(getActivity(), "我点击了详情",
                        Toast.LENGTH_SHORT).show();
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
                refreshLayout.finishRefresh();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载，一般添加调用接口获取更多数据的方法
                    refreshLayout.finishLoadMoreWithNoMoreData();

            }
        });
    }
}

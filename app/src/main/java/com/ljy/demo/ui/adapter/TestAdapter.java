package com.ljy.demo.ui.adapter;



import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ljy.demo.R;
import com.ljy.demo.ui.Entity.TestEntity;
import com.ljy.demo.ui.activity.RecyclerViewActivity;

import java.util.List;

public class TestAdapter extends BaseQuickAdapter<TestEntity.ResultBean.ListBean, BaseViewHolder> {
    /**
     * 构造器，用来初始化TestAdapter
     *
     * @param data 我们的列表数据
     */
    public TestAdapter(@Nullable List<TestEntity.ResultBean.ListBean> data) {
        super(R.layout.item_test, data);
    }

    /**
     * 增加一个构造方法，便于没有数据时候初始化适配器
     */
    public TestAdapter() {
        super(R.layout.item_test);
    }

    /**
     * 继承BaseQuickAdapter后需要重写的方法
     *
     * @param helper view持有者，为重用view而设计，减少每次创建view的内存消耗
     * @param data   我们的列表数据
     */
    @Override
    protected void convert(BaseViewHolder helper, TestEntity.ResultBean.ListBean data) {
        //将每一个需要赋值的id和对应的数据绑定
        helper.setText(R.id.test_item_title, data.getTestTitle())
                .setText(R.id.test_item_message, data.getTestMessage());
        helper.setOnClickListener(R.id.test_item_title, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "我点击了第个的标题",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}

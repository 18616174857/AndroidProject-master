package com.ljy.demo.ui.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ljy.demo.R;
import com.ljy.demo.common.MyActivity;
import com.ljy.demo.ui.adapter.ViewPagerAdapter;
import com.ljy.demo.ui.fragment.CopyFragment;
import com.ljy.demo.ui.fragment.TestFragmentB;
import com.ljy.demo.ui.fragment.TestFragmentC;
import com.ljy.demo.ui.fragment.TestFragmentD;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ViewPageActivity extends MyActivity {

    @BindView(R.id.tl_tabs)
    TabLayout tlTabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_page;
    }

    @Override
    protected void initView() {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        fragments.add(new CopyFragment());
        fragments.add(new TestFragmentB());
        fragments.add(new TestFragmentC());
        fragments.add(new TestFragmentD());
        titles.add("测试1");
        titles.add("测试2");
        titles.add("测试3");
        titles.add("测试4");
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, titles);

        viewPager.setAdapter(viewPagerAdapter);
        tlTabs.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {

    }
}

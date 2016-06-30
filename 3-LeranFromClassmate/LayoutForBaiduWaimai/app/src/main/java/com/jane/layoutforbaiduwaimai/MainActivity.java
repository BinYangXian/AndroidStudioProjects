package com.jane.layoutforbaiduwaimai;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    TabLayout tabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //设置默认状态
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(0).setIcon(mSectionsPagerAdapter.getIconSelected(0));
            tabLayout.getTabAt(1).setIcon(mSectionsPagerAdapter.getIconUnselected(1));
            tabLayout.getTabAt(2).setIcon(mSectionsPagerAdapter.getIconUnselected(2));
        }

        //tab被选中的时候的状态修改
        tabLayout.setOnTabSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getText().equals("首页")) {
            tab.setIcon(R.drawable.tab_icon_home_selected);
            //增加点击tab后界面切换事件
            mViewPager.setCurrentItem(tab.getPosition());
        }
        if (tab.getText().equals("订单")) {
            tab.setIcon(R.drawable.tab_icon_dingdan_selected);
            mViewPager.setCurrentItem(tab.getPosition());
        }
        if (tab.getText().equals("我的")) {
            tab.setIcon(R.drawable.tab_icon_me_selected);
            mViewPager.setCurrentItem(tab.getPosition());
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab.getText().equals("首页")) {
            tab.setIcon(R.drawable.tab_icon_home);
        }
        if (tab.getText().equals("订单")) {
            tab.setIcon(R.drawable.tab_icon_dingdan);
        }
        if (tab.getText().equals("我的")) {
            tab.setIcon(R.drawable.tab_icon_me);
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}

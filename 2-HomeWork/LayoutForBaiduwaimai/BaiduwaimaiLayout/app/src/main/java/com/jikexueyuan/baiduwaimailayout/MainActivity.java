package com.jikexueyuan.baiduwaimailayout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.jikexueyuan.baiduwaimailayout.adapter.SectionsPagerAdapter;
import com.jikexueyuan.baiduwaimailayout.fragment.HomePageFragment;
import com.jikexueyuan.baiduwaimailayout.fragment.OrderPageFrament;
import com.jikexueyuan.baiduwaimailayout.fragment.SettingPageFrament;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public static List<Fragment> myFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化myFragments
        myFragments =new ArrayList<>();
        myFragments.add(new HomePageFragment());
        myFragments.add(new OrderPageFrament());
        myFragments.add(new SettingPageFrament());

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
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


package com.jikexueyuan.baiduwaimailayout.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jikexueyuan.baiduwaimailayout.MainActivity;
import com.jikexueyuan.baiduwaimailayout.R;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {//这个不懂作用
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return MainActivity.myFragments.get(position);

    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return MainActivity.myFragments.size();
    }

    // TODO: 2016/3/1  //下边的提取公因式法 不能成功啊？
//    @Override
//    public CharSequence getPageTitle(int position) {
//
//        return MainActivity.myFragments.get(position).getName();
//
//    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "首页";
            case 1:
                return "订单";
            case 2:
                return "我的";
        }
        return null;
    }
    public int getIconSelected(int position) {
        switch (position) {
            case 0:
                return R.drawable.tab_icon_home_selected;
            case 1:
                return R.drawable.tab_icon_dingdan_selected;
            case 2:
                return R.drawable.tab_icon_me_selected;
        }
        return 1;
    }

    public int getIconUnselected(int position) {
        switch (position) {
            case 0:
                return R.drawable.tab_icon_home;
            case 1:
                return R.drawable.tab_icon_dingdan;
            case 2:
                return R.drawable.tab_icon_me;
        }
        return 1;
    }
}

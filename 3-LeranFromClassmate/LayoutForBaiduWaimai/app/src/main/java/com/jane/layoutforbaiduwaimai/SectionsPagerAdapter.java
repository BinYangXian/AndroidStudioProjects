package com.jane.layoutforbaiduwaimai;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by jane on 16/1/26.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return PlaceholderFragment.newInstance(position);
    }


    @Override
    public int getCount() {

        return 3;
    }

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

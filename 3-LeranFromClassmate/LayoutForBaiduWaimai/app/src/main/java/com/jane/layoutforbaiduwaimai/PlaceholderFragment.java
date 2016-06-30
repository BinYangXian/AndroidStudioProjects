package com.jane.layoutforbaiduwaimai;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jane.layoutforbaiduwaimai.Fragments.FragmentPageDingDan;
import com.jane.layoutforbaiduwaimai.Fragments.FragmentPageHome;
import com.jane.layoutforbaiduwaimai.Fragments.FragmentPageMe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jane on 16/1/26.
 */
public class PlaceholderFragment extends Fragment {
    private SectionsPagerAdapter mAdapter;

    private static List<Fragment> myFragments = new ArrayList<Fragment>();

    public PlaceholderFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        initLayout();

        return myFragments.get(sectionNumber);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewPager myPaper = new ViewPager(getContext());
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        myPaper.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static void initLayout(){
        FragmentPageHome fragmentOne = new FragmentPageHome();
        FragmentPageDingDan fragmentTwo = new FragmentPageDingDan();
        FragmentPageMe fragmentThree = new FragmentPageMe();

        myFragments.add(fragmentOne);
        myFragments.add(fragmentTwo);
        myFragments.add(fragmentThree);
    }
}

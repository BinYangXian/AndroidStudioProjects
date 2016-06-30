package com.jane.layoutforbaiduwaimai.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jane.layoutforbaiduwaimai.R;

/**
 * Created by jane on 16/1/27.
 */
public class FragmentPageDingDan extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = (View) inflater.inflate(R.layout.fragment_dingdan,null);

        return rootView;
    }
}

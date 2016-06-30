package com.jikexueyuan.baiduwaimailayout.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jikexueyuan.baiduwaimailayout.R;

/**
 * Created by fangc on 2016/3/1.
 */
public class OrderPageFrament extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = null;
        rootView = inflater.inflate(R.layout.order_fragment, container, false);
        return rootView;
    }
}

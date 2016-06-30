package com.jikexueyuan.layoutforbaiduwaimaireview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fangc on 2016/4/1.
 */
public class MySettingPage extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.setting_page_frament, container, false);
        return rootView;
    }
}

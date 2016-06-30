package com.jikexueyuan.learnfragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by fangc on 2016/2/20.
 */
public class Image1Fm extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView imageView=new ImageView(getActivity());//动态创建imageView对象
        imageView.setImageResource(R.drawable.img1);
        return imageView;
    }
}

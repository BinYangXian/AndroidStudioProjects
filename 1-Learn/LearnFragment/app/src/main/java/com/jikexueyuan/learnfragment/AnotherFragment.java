package com.jikexueyuan.learnfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fangc on 2016/2/20.
 */
public class AnotherFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //初始化此Fragment的布局,利用外界传进来的inflater,参数：需要解析的布局来源、主布局
        View root = inflater.inflate(R.layout.fragment_another, container, false);
        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();//把后退栈里边的最后一项给移除
            }
        });
        return root;
    }
}

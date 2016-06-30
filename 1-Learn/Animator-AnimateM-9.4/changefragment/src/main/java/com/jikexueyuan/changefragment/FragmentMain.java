package com.jikexueyuan.changefragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fangc on 2016/3/5.
 */
public class FragmentMain extends Fragment implements View.OnClickListener {
    private View root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main,null);

        root.findViewById(R.id.btnShowAnotherFragment).setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.enter,R.animator.exit,R.animator.enter,R.animator.exit)//
                // 参数：1/2、第二页面进入与第一页面退出的效果（3/4、第一页面进入，与第二页面退出的效果）;
                .addToBackStack("AnotherFragment")
                .replace(R.id.container, new AnotherFragment())
                .commit();
    //此处仅addToBackStack(将该Fragment添加到后退栈里)，并不能像supportv7里能直接实现后退动作，
    // 需要MainActivity中配合onBackPressed方法。
    }
}

package com.jikexueyuan.changefragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by fangc on 2016/3/5.
 */
public class AnotherFragment extends Fragment {
    private View root;
    private Button btnBack;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_another,null);

        btnBack= (Button) root.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return root;
    }
}

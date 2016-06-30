package com.jikexueyuan.learnfragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by fangc on 2016/2/20.
 */
public class JikexueyuanWebViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.jikexueyuan_webview,container,false);
        WebView webView= (WebView) root.findViewById(R.id.wv);
        webView.loadUrl("http://www.jikexueyuan.com");
        return root;
    }
}
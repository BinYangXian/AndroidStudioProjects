package com.jikexueyuan.usingvaluesdirectory;

import android.preference.PreferenceActivity;

import java.util.List;

/**
 * Created by fangc on 2016/3/23.
 */
//关于页面设置的话建议继承PreferenceActivity，同时建议使用fragment
public class SettingsActivity extends PreferenceActivity{

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.settings_preference,target);//加载布局资源（其中含settingsFragment），类似setContentView
        // 到本Activity。
    }
}

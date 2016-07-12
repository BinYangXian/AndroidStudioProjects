package com.jikexueyuan.usingvaluesdirectory;

import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import java.util.List;

/**
 * Created by fangc on 2016/3/23.
 */
//关于页面设置的话建议继承PreferenceActivity，同时建议使用fragment
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences preferences;

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.settings_preference,target);//加载布局
    }

    // TODO: 2016/3/23 谷歌官方建议：监听偏好设置信息的改变，在onResume与onPause方法中
    @Override
    protected void onResume() {
        super.onResume();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);//监听该SettingsActivity类，其需要同时实现此相关接口
    }

    @Override
    protected void onPause() {
        super.onPause();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        System.out.println("更改了"+key);
    }
}

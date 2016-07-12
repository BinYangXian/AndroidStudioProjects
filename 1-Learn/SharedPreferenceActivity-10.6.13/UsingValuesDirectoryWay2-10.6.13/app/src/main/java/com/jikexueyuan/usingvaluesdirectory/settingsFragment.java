package com.jikexueyuan.usingvaluesdirectory;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by fangc on 2016/3/23.
 */
public class settingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.settings_preference, false);//参数1，getActivity()
        // 的前提是在SettingsActivity加载了此类；参数2：初始界面；参数3为false时：当我无论怎么退出程序后再进入，默认设置(checkbox_preference)
        // 的选中状态都不会改变。
    }

    public static class notificationFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            System.out.println("notificationFragment");
            System.out.println(getArguments().get("notification"));//输出为xml文件中配置的extra “1234”

            addPreferencesFromResource(R.xml.settings_notification);//注意这个添加的功能的整体是在
            // 本类（notificationFragment）中的，也就是说，当点击preference-headers中的第一个才会出现此功能。（相当于该
            // preference-headers被点击时，启动了notificationFragment中的onCreate方法）
        }
    }
    public static class notificationTimeFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            System.out.println("notificationTimeFragment");

            addPreferencesFromResource(R.xml.settings_notification_time);
        }
    }
}

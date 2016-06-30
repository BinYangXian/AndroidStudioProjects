package com.jikexueyuan.acceleratebyonekey;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private long initializeMemory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cleanSystemMemory();
        finish();
    }

    private void cleanSystemMemory() {
        ActivityManager activityManger = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = activityManger.getRunningAppProcesses();
        initializeMemory = getAvailMemory(getBaseContext());
        if (list != null)
            for (int i = 0; i < list.size(); i++) {
                ActivityManager.RunningAppProcessInfo apinfo = list.get(i);

//                System.out.println("pid            " + apinfo.pid);
//                System.out.println("processName              " + apinfo.processName);
//                System.out.println("importance            " + apinfo.importance);
                String[] pkgList = apinfo.pkgList;

                if (apinfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE) {
                    // Process.killProcess(apinfo.pid);
                    for (String aPkgList : pkgList) {
                        //2.2以上restartPackage是过时的,请用killBackgroundProcesses代替
                        activityManger.killBackgroundProcesses(aPkgList);
                    }
                }
            }
        Toast.makeText(getBaseContext(), getAvailMemory(getBaseContext()) - initializeMemory + "", Toast.LENGTH_SHORT).show();
    }

    private long getAvailMemory(Context context) {
        // 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存

        //return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
        return mi.availMem / (1024 * 1024);
        // 2016/3/16 第一次从网络借鉴而得的作业
        //这是test分支上的代码
    }

}

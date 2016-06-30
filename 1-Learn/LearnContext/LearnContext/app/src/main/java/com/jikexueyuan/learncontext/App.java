package com.jikexueyuan.learncontext;

import android.app.Application;

/**
 * Created by fangc on 2016/1/4.
 */
public class App extends Application {
    private String textdate="default";

    public String getTextdate() {
        return textdate;
    }

    public void setTextdate(String textdate) {
        this.textdate = textdate;
    }
}

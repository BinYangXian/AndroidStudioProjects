package com.jikexueyuan.learncontext2;

import android.app.Application;
import android.widget.TextView;

/**
 * Created by fangc on 2016/1/5.
 */
public class App extends Application {
    private String textDate="default";

    public String getTextDate() {
        return textDate;
    }

    public void setTextDate(String textDate) {
        this.textDate = textDate;
    }
}

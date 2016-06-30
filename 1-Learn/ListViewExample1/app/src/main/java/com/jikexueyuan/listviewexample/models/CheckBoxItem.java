package com.jikexueyuan.listviewexample.models;

/**
 * Created by fangc on 2016/2/21.
 */
public class CheckBoxItem {
    private String label;
    private boolean checked=false;

    public CheckBoxItem(String label, boolean checked) {
        this.label = label;
        this.checked = checked;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isChecked() {

        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return label;
    }
}

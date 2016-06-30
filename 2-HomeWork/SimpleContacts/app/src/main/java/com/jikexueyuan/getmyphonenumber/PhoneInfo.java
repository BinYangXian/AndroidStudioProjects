package com.jikexueyuan.getmyphonenumber;

/**
 * Created by fangc on 2016/1/29.
 */
public class PhoneInfo {//将一个联系人名与一个号码造型为自定义PhoneInfo类型，并创建相应构造、访问方法
    private String name;
    private String number; //number有地区之分例如+86

    public PhoneInfo(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

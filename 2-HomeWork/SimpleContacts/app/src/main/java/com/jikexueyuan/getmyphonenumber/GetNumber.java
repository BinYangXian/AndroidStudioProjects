package com.jikexueyuan.getmyphonenumber;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fangc on 2016/1/29.
 */
public class GetNumber {
    public static List<PhoneInfo> lists = new ArrayList<>(); //为了把从SQlite中获取的电话号码与联系人的数据进行封装

    public static String getNumber(Context context) {  //1.创建获取SQlite中通讯录的方法，传入一个context（上下文对象供下述用）
        //数据库返回的数据类型都是Cursor,所以下述用此类型，查询出联系数据包中的公共数据类型中的电话中的CONTENT_URI,并不设置过滤
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        String phoneNumber;
        String phoneName;
        assert cursor != null;
        cursor.moveToFirst();
        lists.clear();// 清楚重复显示
        for (int i = 0; i < cursor.getCount(); i++) {
//        while (cursor.moveToNext()){  //循环，直到指针无法走到下一条，这样在这里可能没有问题，在别的版本cursor，
            // 最开始可能不是指向第一个查询结果,因为moveToNext()后，已经移动到第二条数据了。
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));//从SQlite中获取的电话号码与联系人的数据
            phoneName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            PhoneInfo phoneInfo = new PhoneInfo(phoneName, phoneNumber);//将一个联系人名与一个号码造型为自定义phoneInfo类型
            lists.add(phoneInfo); //将自定义数据类型phoneInfo存在list数组集合中
            System.out.println(phoneName + phoneNumber);  //测试输出
            cursor.moveToNext();
        }
        cursor.close();//不关闭的话，会有内存泄漏，现在的android 会自动关闭了
        return null;
    }
}

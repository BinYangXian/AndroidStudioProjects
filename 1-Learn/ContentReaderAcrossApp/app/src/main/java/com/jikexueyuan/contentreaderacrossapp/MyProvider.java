package com.jikexueyuan.contentreaderacrossapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by fangc on 2016/1/29.   Review and upData on 2016/2/27.
 */
public class MyProvider extends ContentProvider {//创建自定义数据源类型
    public static final Uri URI=Uri.parse("content://com.jikexueyuan.cp");//这个Uri是 import android.net.Uri;这个；注意content：//；
    // public static方便外部访问
    SQLiteDatabase database;  //创建SQL Database数据库的一个database引用
    @Override
    public boolean onCreate() {
        database=getContext().openOrCreateDatabase("mycp.db3", Context.MODE_PRIVATE,null);//创建数据库，参数1.数据库文件名称
        // 2.让此数据库文件只能被当前类调用
        database.execSQL("create table tab(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)");//参数为sql语言的字符串

        return true;//返回值改为true
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor=database.query("tab",null,null,null,null,null,null);//将查询结果返回
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {//在数据表中插入数据需要的是第二个参数
        database.insert("tab","_id",values);
//        database.close();
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
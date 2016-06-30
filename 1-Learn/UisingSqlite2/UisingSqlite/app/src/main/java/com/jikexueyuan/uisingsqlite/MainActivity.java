package com.jikexueyuan.uisingsqlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Db db=new Db(this);
        SQLiteDatabase dbWrite =db.getWritableDatabase();
        ContentValues cv=new ContentValues();//ContentValues表示android封装的一条数据
                                              //user表示指明插入数据表的名字这里的null表明；nullColumnHack表示
        cv.put("name", "红红");              // 指明覆盖Db中的sex，我们这里用null表示不覆盖；values表示
        cv.put("sex","女");                  // content的一个对象
        dbWrite.insert("user",null,cv);
        cv=new ContentValues();//ContentValues表示android封装的一条数据
                                              //user表示指明插入数据表的名字这里的null表明；nullColumnHack表示
        cv.put("name", "莹莹");              // 指明覆盖Db中的sex，我们这里用null表示不覆盖；values表示
        cv.put("sex","女");                  // content的一个对象
        dbWrite.insert("user",null,cv);
        dbWrite.close();                      //确定不用的时候，最好手动关闭数据库，别让系统来判断
    }
}

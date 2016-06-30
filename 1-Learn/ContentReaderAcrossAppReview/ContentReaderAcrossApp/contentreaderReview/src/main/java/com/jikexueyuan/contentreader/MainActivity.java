package com.jikexueyuan.contentreader;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cursor cursor=getContentResolver().query(Uri.parse("content://com.jikexueyuan.cp"), null, null, null, null);
        assert cursor != null;
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()){
            Toast.makeText(this,cursor.getString(cursor.getColumnIndex("name")), Toast.LENGTH_SHORT).show();
        }
    }
}

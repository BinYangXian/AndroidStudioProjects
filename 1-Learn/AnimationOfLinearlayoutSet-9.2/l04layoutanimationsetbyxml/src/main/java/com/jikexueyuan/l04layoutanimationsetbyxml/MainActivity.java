package com.jikexueyuan.l04layoutanimationsetbyxml;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
//6 //使用资源文件配置布局动画，更为简便，且使得xml易于复用。（见@/anim/listview_anim与scale_0_1文件，而它们的使用在layout文件夹中直接通过@/引用名称就行了。）
// 可替代l03layoutanimationinlistview中的代码配置！！！！！！！！！！！！！！！！！！！！！！！！！！
public class MainActivity extends ListActivity {
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{"hello","world","jikexueyuan"} );
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

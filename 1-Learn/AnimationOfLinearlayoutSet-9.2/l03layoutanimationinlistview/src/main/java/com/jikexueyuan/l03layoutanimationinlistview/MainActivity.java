package com.jikexueyuan.l03layoutanimationinlistview;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
//5 为列表添加布局动画效果,与添加Layout的动画效果同理，包括GridView等，因为它们都是View的子类。
public class MainActivity extends ListActivity {

    private ArrayAdapter<String> adapter;
    private LayoutAnimationController lac;
    private Animation sa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{"hello","world","jikexueyuan"} );
        setListAdapter(adapter);

        sa=new ScaleAnimation(0,1,0,1);
        sa.setDuration(1000);
        lac=new LayoutAnimationController(sa,0.5f);

        getListView().setLayoutAnimation(lac); //获取到此activity所关联的listView对象。
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

package com.jikexueyuan.messageblockingreview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "编辑").setIcon(android.R.drawable.ic_menu_edit);
        // return true才会起作用
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST + 2:
//                startActivity(new Intent(MainActivity.this, EditActivityView.class));
                Toast.makeText(this, "启动", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
